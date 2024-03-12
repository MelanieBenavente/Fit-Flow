package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AddSerieUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<AddSerieUseCaseParams, List<SerieModel>>(){
    override fun run(params: AddSerieUseCaseParams): Flow<List<SerieModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val series = fitnFlowRepository.addNewSerie(params.reps, params.weight, params.exerciseId, apiKey)
        emit(series)
    }
}

data class AddSerieUseCaseParams(val reps: Int, val weight: Double, val exerciseId: Int)