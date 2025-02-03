package app.fit.fitndflow.data.trainings.repositoryImpl

import android.content.Context
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
    private val trainingLocalDataSource: TrainingLocalDataSource
) : TrainingRepository {
    @Throws(Exception::class)
    fun getSerieListOfExerciseAdded(exerciseId: Int): List<SerieModel>? {
        try {
            val categoryList = trainingLocalDataSource.getTrainingsByDate(
                trainingLocalDataSource.currentDate!!
            )
            if (categoryList != null) {
                for (i in categoryList.indices) {
                    val category = categoryList[i]
                    val exerciseList: List<ExerciseModel>? = category.exerciseList
                    for (j in exerciseList!!.indices) {
                        val exercise = exerciseList[j]
                        if (exercise.id == exerciseId) {
                            return exercise.serieList
                        }
                    }
                }
            }
            return ArrayList()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
    }

    @Throws(Exception::class)
    fun addNewSerie(reps: Int, weight: Double, exerciseId: Int): ExerciseModel {
        val exerciseResponse: ExerciseModel
        val response: ExerciseDto?
        try {
            response = trainingRemoteDataSource.addNewSerie(
                reps,
                weight,
                exerciseId,
                trainingLocalDataSource.currentDate!!
            )
            exerciseResponse = toModel(response!!)
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return exerciseResponse
    }

    @Throws(Exception::class)
    fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseModel {
        val exerciseResponse: ExerciseModel
        val response: ExerciseDto?
        try {
            response = trainingRemoteDataSource.modifySerie(serieId, reps, weight)
            exerciseResponse = toModel(response!!)
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return exerciseResponse
    }

    @Throws(Exception::class)
    fun deleteSerie(serieId: Int): ExerciseModel {
        val exerciseResponse: ExerciseModel
        val response: ExerciseDto?
        try {
            response = trainingRemoteDataSource.deleteSerie(serieId)
            exerciseResponse = toModel(response!!)
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return exerciseResponse
    }

    @Throws(Exception::class)
    fun getTrainingListAndUpdateCache(date: String?): List<CategoryModel>? {
        trainingLocalDataSource.currentDate = date
        val response: List<CategoryDto>
        if (trainingLocalDataSource.getTrainingsByDate(date!!) == null) {
            try {
                response = trainingRemoteDataSource.getTrainingListAndUpdateCache(date)
                trainingLocalDataSource.replaceAllDataFromTrainingCache(date, toModel(response))
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return trainingLocalDataSource.getTrainingsByDate(date)
    }

    @Throws(Exception::class)
    fun updateCurrentTrainingListCache(): List<CategoryModel>? {
        return getTrainingListAndUpdateCache(trainingLocalDataSource.currentDate)
    }
}
