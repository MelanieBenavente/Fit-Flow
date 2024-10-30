package com.fit.fitndflow.app.domain.exercises.exercisesUseCases

import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyExerciseUseCase @Inject constructor(val exercisesRepository: ExercisesRepository) : UseCase<ExerciseModelInLanguages, List<ExerciseModel>>() {
    override fun run(params: ExerciseModelInLanguages): Flow<List<ExerciseModel>> = flow {
        val exerciseModified = exercisesRepository.modifyExercise(params.exerciseId, params.exerciseName, params.language, params.categoryId)
        emit(exerciseModified)
    }
}
data class ExerciseModelInLanguages(val exerciseId : Int, val exerciseName : String, val language: String, val categoryId : Int)
