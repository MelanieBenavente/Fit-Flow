package com.fit.fitndflow.app.domain.exercises.exercisesUseCases

import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddExerciseUseCase @Inject constructor(val exercisesRepository: ExercisesRepository) : UseCase<AddExerciseUseCaseParams, List<ExerciseModel>>() {
    override fun run(params: AddExerciseUseCaseParams): Flow<List<ExerciseModel>>  = flow {
        val newExercise = exercisesRepository.addNewExercise(params.name, params.language, params.categoryId)
        emit(newExercise)
    }
}
data class AddExerciseUseCaseParams(var name : String, val language: String, var categoryId : Int)
