package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.ExercisesRepository
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(val exercisesRepository: ExercisesRepository) : UseCase<ExerciseToDeleteParams, List<ExerciseModel>>() {
    override fun run(params: ExerciseToDeleteParams): Flow<List<ExerciseModel>> = flow {
        val exerciseList = exercisesRepository.deleteExercise(params.exerciseId)
        emit(exerciseList)
    }
}

data class ExerciseToDeleteParams(val exerciseId: Int)