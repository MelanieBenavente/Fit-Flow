package app.fit.fitndflow.data.trainings.repositoryImpl

import android.content.Context
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.dao.SerieDao
import app.fit.fitndflow.data.common.database.dao.TrainingDao
import app.fit.fitndflow.data.common.database.entities.SerieEntity
import app.fit.fitndflow.data.common.database.entities.filterTrainingByDate
import app.fit.fitndflow.data.common.database.mapper.toModel
import app.fit.fitndflow.data.common.datasource.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.ExerciseDto
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
    private val trainingLocalDataSource: TrainingLocalDataSource,
    private val serieDao: SerieDao,
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
    private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
) : TrainingRepository {
    private val isLocalMode
        get() = sharedPrefsLocalDataSource.getIsLocal()

    override suspend fun getSerieListOfExerciseAdded(exerciseId: Int): List<SerieModel> {
        return try {
            val currentDate = trainingLocalDataSource.currentDate ?: return emptyList()
            val categoryList = trainingLocalDataSource.getTrainingsByDate(currentDate)
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
            trainingLocalDataSource.currentDate?.let { currentDate ->
                if (isLocalMode) {
                    serieDao.insertSerie(
                        SerieEntity(
                            exerciseId = exerciseId,
                            reps = reps,
                            weight = weight,
                            date = currentDate
                        )
                    )
                    val record = exerciseDao.getExercise(exerciseId).firstOrNull()?.record
                    response =
                        serieDao.getExerciseWithSeriesByDate(exerciseId, currentDate).toModel(record)
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
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseModel? {
        var response: ExerciseModel? = null
        try {
            trainingLocalDataSource.currentDate?.let { currentDate ->
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
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun deleteSerie(serieId: Int): ExerciseModel? {
        var response: ExerciseModel? = null
        try {
            trainingLocalDataSource.currentDate?.let { currentDate ->
                if (isLocalMode) {
                    val exerciseId = serieDao.getSerie(serieId).exerciseId
                    serieDao.deleteSerie(serieId)
                    response = serieDao.getExerciseWithSeries(exerciseId,currentDate).first().toModel()
                } else {
                    trainingRemoteDataSource.deleteSerie(serieId)?.let { response = toModel(it) }
                }
            }
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun getTrainingListAndUpdateCache(date: String): List<CategoryModel> {
        trainingLocalDataSource.currentDate = date
        val response: List<CategoryModel>
        if (trainingLocalDataSource.getTrainingsByDate(date) == null) {
            try {
                if (isLocalMode){
                    response = filterTrainingByDate(trainingDao.getFullTraining(), date).map { it.toModel() }
                } else {
                    response = toModel(trainingRemoteDataSource.getTrainingListAndUpdateCache(date))
                }
                    trainingLocalDataSource.replaceAllDataFromTrainingCache(date, response)
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return trainingLocalDataSource.getTrainingsByDate(date).orEmpty()
    }

    override suspend fun updateCurrentTrainingListCache(): List<CategoryModel> {
        return getTrainingListAndUpdateCache(trainingLocalDataSource.currentDate.orEmpty())
    }
}
