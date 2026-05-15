package app.fit.fitndflow.data.exercises.repositoryImpl

import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import app.fit.fitndflow.data.common.database.mapper.toModel
import app.fit.fitndflow.data.common.datasource.local.CategoriesAndExercisesCacheLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import app.fit.fitndflow.data.common.mapper.ExerciseModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.common.mapper.convertToStringInLanguages
import app.fit.fitndflow.data.exercises.datasource.remote.ExerciseRemoteDataSource
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository

class ExercisesRepositoryImpl(
    private val exerciseRemoteDataSource: ExerciseRemoteDataSource,
    private val exerciseDao: ExerciseDao,
    private val categoriesAndExercisesCacheLocalDataSource: CategoriesAndExercisesCacheLocalDataSource,
    private val trainingCacheLocalDataSource: TrainingCacheLocalDataSource,
    private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
) : ExercisesRepository {
    private val isLocalMode
        get() = sharedPrefsLocalDataSource.getIsLocal()

    override suspend fun addNewExercise(
        exerciseName: String,
        language: String,
        categoryId: Int
    ): List<ExerciseModel> {
        val stringInLanguages = convertToStringInLanguages(language, exerciseName)
        val response: List<ExerciseModel>
        try {
            if (isLocalMode) {
                exerciseDao.insertExercise(
                    ExerciseEntity(
                        categoryId = categoryId,
                        nameEs = stringInLanguages.spanish.orEmpty(),
                        nameEn = stringInLanguages.english.orEmpty(),
                        recordHeight = 0.0,
                        recordReps = 0
                    )
                )
                response = exerciseDao.getAllExercisesByCategory(categoryId).map { it.toModel() }
            } else {
                response =
                    toModel(exerciseRemoteDataSource.addNewExercise(stringInLanguages, categoryId))
            }
            //TODO OJO! Aquí no se está limpiando caché
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun modifyExercise(
        exerciseId: Int,
        exerciseName: String,
        language: String,
        categoryId: Int
    ): List<ExerciseModel> {
        val stringInLanguages = convertToStringInLanguages(language, exerciseName)
        val response: List<ExerciseModel>
        try {
            if (isLocalMode) {
                exerciseDao.updateExercise(
                    exerciseId = exerciseId,
                    nameEs = stringInLanguages.spanish.orEmpty(),
                    nameEn = stringInLanguages.english.orEmpty(),
                    categoryId = categoryId
                )
                response = exerciseDao.getAllExercisesByCategory(categoryId).map { it.toModel() }
            } else {
                response = toModel(
                    exerciseRemoteDataSource.modifyExercise(
                        exerciseId,
                        stringInLanguages,
                        categoryId
                    )
                )
            }
            trainingCacheLocalDataSource.cleanCache()
            categoriesAndExercisesCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }

    override suspend fun deleteExercise(exerciseId: Int): List<ExerciseModel> {
        val response: List<ExerciseModel>
        try {
            if(isLocalMode) {
                val categoryId = exerciseDao.getExercise(exerciseId).first().categoryId
                exerciseDao.deleteExercise(exerciseId)
                response = exerciseDao.getAllExercisesByCategory(categoryId).map { it.toModel() }
            } else {
                response = toModel(exerciseRemoteDataSource.deleteExercise(exerciseId))
            }
            trainingCacheLocalDataSource.cleanCache()
            categoriesAndExercisesCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return response
    }
}
