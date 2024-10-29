package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.ExercisesRepository
import app.fit.fitndflow.domain.repository.FitnFlowRepository
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
