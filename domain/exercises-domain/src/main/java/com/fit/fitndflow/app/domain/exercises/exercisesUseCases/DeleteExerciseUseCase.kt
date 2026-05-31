package com.fit.fitndflow.app.domain.exercises.exercisesUseCases

import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(private val exercisesRepository: ExercisesRepository) : UseCase<ExerciseToDeleteParams, List<ExerciseModel>>() {
    override fun run(params: ExerciseToDeleteParams): Flow<List<ExerciseModel>> = flow {
        val exerciseList = exercisesRepository.deleteExercise(params.exerciseId)
        emit(exerciseList)
    }
}

data class ExerciseToDeleteParams(val exerciseId: Int)