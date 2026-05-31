package app.fit.fitndflow.data.exercises.datasource.remote

import app.fit.fitndflow.data.common.dto.ExerciseDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import app.fit.fitndflow.data.common.model.ExcepcionApi
import app.fit.fitndflow.data.exercises.datasource.ExerciseDataSourceInterface
import app.fit.fitndflow.data.exercises.dto.AddExerciseDto
import app.fit.fitndflow.data.exercises.dto.ModifyExerciseDto
import app.fit.fitndflow.data.exercises.model.ExercisesApiInterface

class ExerciseRemoteDataSource(private val exercisesApiInterface: ExercisesApiInterface) : ExerciseDataSourceInterface {
    override fun addNewExercise(
        exerciseName: StringInLanguagesDto,
        categoryId: Int
    ): List<ExerciseDto> {
        val addExerciseDto = AddExerciseDto(exerciseName, categoryId)
        val response = exercisesApiInterface.addNewExercise(addExerciseDto).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }

    override fun modifyExercise(
        exerciseId: Int,
        exerciseName: StringInLanguagesDto,
        categoryId: Int
    ): List<ExerciseDto> {
        val modifyExerciseDto = ModifyExerciseDto(exerciseId, exerciseName, categoryId)
        val response = exercisesApiInterface.modifyExercise(modifyExerciseDto).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }

    override fun deleteExercise(exerciseId: Int): List<ExerciseDto> {
        val response = exercisesApiInterface.deleteExercise(exerciseId).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }
}