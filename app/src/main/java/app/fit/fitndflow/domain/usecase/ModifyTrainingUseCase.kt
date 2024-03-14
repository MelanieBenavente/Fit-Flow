package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ModifyTrainingUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<ModifySerieUseCaseParams, List<SerieModel>>() {
    override fun run(params: ModifySerieUseCaseParams): Flow<List<SerieModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val series = fitnFlowRepository.modifySerie(params.serieId, params.reps, params.weight, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(series)
    }
}
data class ModifySerieUseCaseParams(val serieId: Int, val reps: Int, val weight: Double)