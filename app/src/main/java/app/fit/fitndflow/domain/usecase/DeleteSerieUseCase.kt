package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteSerieUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context): UseCase<GetSerieToDeleteParams, List<SerieModel>>(){
    override fun run(params: GetSerieToDeleteParams): Flow<List<SerieModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val serieDeleted = fitnFlowRepository.deleteSerie(params.serieId, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(serieDeleted)
    }
}

data class GetSerieToDeleteParams(val serieId: Int)