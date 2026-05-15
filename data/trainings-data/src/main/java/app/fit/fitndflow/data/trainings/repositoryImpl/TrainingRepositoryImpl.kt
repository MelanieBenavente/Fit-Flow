package app.fit.fitndflow.data.trainings.repositoryImpl

import android.content.Context
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.dao.SerieDao
import app.fit.fitndflow.data.common.database.dao.TrainingDao
import app.fit.fitndflow.data.common.database.entities.SerieEntity
import app.fit.fitndflow.data.common.database.entities.filterTrainingByDate
import app.fit.fitndflow.data.common.database.mapper.toModel
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.common.mapper.ExerciseModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.trainings.datasource.remote.TrainingRemoteDataSource
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository

class TrainingRepositoryImpl(
    private val mContext: Context,
    private val trainingRemoteDataSource: TrainingRemoteDataSource,
    private val trainingCacheLocalDataSource: TrainingCacheLocalDataSource,
    private val serieDao: SerieDao,
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
    private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
) : TrainingRepository {
    private val isLocalMode
        get() = sharedPrefsLocalDataSource.getIsLocal()

    override suspend fun getSerieListOfExerciseAdded(exerciseId: Int): List<SerieModel> {
        return try {
            val currentDate = trainingCacheLocalDataSource.currentDate ?: return emptyList()
            val categoryList = trainingCacheLocalDataSource.getTrainingsByDate(currentDate)
            categoryList?.forEach { category ->
                category.exerciseList?.forEach { exercise ->
                    if (exercise.id == exerciseId) {
                        return exercise.serieList.toList()
                    }
                }
            }
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
    }

    override suspend fun addNewSerie(reps: Int, weight: Double, exerciseId: Int): ExerciseModel? {
        var response: ExerciseModel? = null
        try {
            trainingCacheLocalDataSource.currentDate?.let { currentDate ->
                if (isLocalMode) {
                    if(getSerieListOfExerciseAdded(exerciseId).isEmpty()) exerciseDao.updateFirstSerie(exerciseId, reps, weight)

                    serieDao.insertSerie(
                        SerieEntity(
                            exerciseId = exerciseId,
                            reps = reps,
                            weight = weight,
                            date = currentDate
                        )
                    )
                    val exercise = exerciseDao.getExercise(exerciseId).firstOrNull()
                    val currentRecord = exercise
                        ?.takeUnless { it.recordHeight == 0.0 && it.recordReps == 0 }
                        ?.let { SerieModel(reps = it.recordReps, kg = it.recordHeight, isRecord = true) }
                    val isRecord = currentRecord == null ||
                        weight > (currentRecord.kg ?: 0.0) ||
                        (weight >= (currentRecord.kg ?: 0.0) && reps > (currentRecord.reps ?: 0))
                    val lastRecord = if (isRecord) {
                        SerieModel(reps = reps, kg = weight, isRecord = true)
                    } else {
                        currentRecord
                    }
                    if (isRecord) { exerciseDao.updateRecord(exerciseId, weight, reps) }
                    response =
                        serieDao.getExerciseWithSeriesByDate(exerciseId, currentDate)
                            .toModel(lastRecord)
                } else {
                    trainingRemoteDataSource.addNewSerie(
                        reps,
                        weight,
                        exerciseId,
                        currentDate
                    )?.let {
                        response = toModel(it)
                    }
                }
            }
            trainingCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseModel? {
        var response: ExerciseModel? = null
        try {
            trainingCacheLocalDataSource.currentDate?.let { currentDate ->
                if (isLocalMode) {
                    val exerciseId = serieDao.getSerie(serieId).exerciseId
                    serieDao.updateSerie(
                        serieId = serieId,
                        reps = reps,
                        weight = weight
                    )
                    response =
                        serieDao.getExerciseWithSeries(exerciseId, currentDate).first().toModel()
                } else {
                    trainingRemoteDataSource.modifySerie(serieId, reps, weight)?.let { response = toModel(it) }
                }
            }
            trainingCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun deleteSerie(serieId: Int): ExerciseModel? {
        var response: ExerciseModel? = null
        try {
            trainingCacheLocalDataSource.currentDate?.let { currentDate ->
                if (isLocalMode) {
                    val exerciseId = serieDao.getSerie(serieId).exerciseId
                    serieDao.deleteSerie(serieId)
                    response = serieDao.getExerciseWithSeries(exerciseId,currentDate).first().toModel()
                } else {
                    trainingRemoteDataSource.deleteSerie(serieId)?.let { response = toModel(it) }
                }
            }
            trainingCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun getTrainingListAndUpdateCache(date: String): List<CategoryModel> {
        trainingCacheLocalDataSource.currentDate = date
        val response: List<CategoryModel>
        if (trainingCacheLocalDataSource.getTrainingsByDate(date) == null) {
            try {
                if (isLocalMode){
                    response = filterTrainingByDate(trainingDao.getFullTraining(), date).map { it.toModel() }
                } else {
                    response = toModel(trainingRemoteDataSource.getTrainingListAndUpdateCache(date))
                }
                    trainingCacheLocalDataSource.replaceAllDataFromTrainingCache(date, response)
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return trainingCacheLocalDataSource.getTrainingsByDate(date).orEmpty()
    }

    override suspend fun updateCurrentTrainingListCache(): List<CategoryModel> {
        return getTrainingListAndUpdateCache(trainingCacheLocalDataSource.currentDate.orEmpty())
    }
}
