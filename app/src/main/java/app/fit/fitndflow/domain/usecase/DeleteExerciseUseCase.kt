package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteExerciseUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<ExerciseToDeleteParams, List<ExerciseModel>>() {
    override fun run(params: ExerciseToDeleteParams): Flow<List<ExerciseModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val exerciseToDelete = fitnFlowRepository.deleteExercise(params.exerciseId, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(exerciseToDelete)
    }
}

data class ExerciseToDeleteParams(val exerciseId: Int)