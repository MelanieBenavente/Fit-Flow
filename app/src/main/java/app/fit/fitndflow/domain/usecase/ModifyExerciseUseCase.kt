package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ModifyExerciseUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<ExerciseModelInLanguages, List<ExerciseModel>>() {
    override fun run(params: ExerciseModelInLanguages): Flow<List<ExerciseModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val exerciseModified = fitnFlowRepository.modifyExercise(params.exerciseId, params.exerciseName, params.language, params.categoryId, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(exerciseModified)
    }
}
data class ExerciseModelInLanguages(val exerciseId : Int, val exerciseName : String, val language: String, val categoryId : Int)
