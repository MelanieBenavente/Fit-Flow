package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddExerciseUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<AddExerciseUseCaseParams, List<ExerciseModel>>() {
    override fun run(params: AddExerciseUseCaseParams): Flow<List<ExerciseModel>>  = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val newExercise = fitnFlowRepository.addNewExercise(params.name, params.language, params.categoryId, apiKey)
        emit(newExercise)
    }
}
data class AddExerciseUseCaseParams(var name : String, val language: String, var categoryId : Int)
