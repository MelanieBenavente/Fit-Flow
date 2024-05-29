package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyExerciseUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<ExerciseModelInLanguages, List<ExerciseModel>>() {
    override fun run(params: ExerciseModelInLanguages): Flow<List<ExerciseModel>> = flow {
        val exerciseModified = fitnFlowRepository.modifyExercise(params.exerciseId, params.exerciseName, params.language, params.categoryId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(exerciseModified)
    }
}
data class ExerciseModelInLanguages(val exerciseId : Int, val exerciseName : String, val language: String, val categoryId : Int)
