package app.fit.fitndflow.data.exercises.datasource

import app.fit.fitndflow.data.common.dto.ExerciseDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto

interface ExerciseDataSourceInterface {

    fun addNewExercise(exerciseName: StringInLanguagesDto, categoryId: Int) : List<ExerciseDto>

    fun modifyExercise(exerciseId: Int, exerciseName: StringInLanguagesDto, categoryId: Int) : List<ExerciseDto>

    fun deleteExercise(exerciseId: Int) : List<ExerciseDto>
}
