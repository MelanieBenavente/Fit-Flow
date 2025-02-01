package com.fit.fitndflow.app.domain.exercises.repository

import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.repository.CommonRepository

interface ExercisesRepository : CommonRepository {
    suspend fun addNewExercise(
        exerciseName: String,
        language: String,
        categoryId: Int
    ): List<ExerciseModel>

    suspend fun modifyExercise(
        exerciseId: Int,
        exerciseName: String,
        language: String,
        categoryId: Int
    ): List<ExerciseModel>

    suspend fun deleteExercise(exerciseId: Int): List<ExerciseModel>
}
