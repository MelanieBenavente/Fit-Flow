package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<ExerciseToDeleteParams, List<ExerciseModel>>() {
    override fun run(params: ExerciseToDeleteParams): Flow<List<ExerciseModel>> = flow {
        val exerciseToDelete = fitnFlowRepository.deleteExercise(params.exerciseId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(exerciseToDelete)
    }
}

data class ExerciseToDeleteParams(val exerciseId: Int)