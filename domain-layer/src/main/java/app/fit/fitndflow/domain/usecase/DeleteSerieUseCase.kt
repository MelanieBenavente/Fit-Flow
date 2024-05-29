package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieInfoWrapper
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSerieUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository): UseCase<GetSerieToDeleteParams, SerieInfoWrapper>(){
    override fun run(params: GetSerieToDeleteParams): Flow<SerieInfoWrapper> = flow {
        val exercise = fitnFlowRepository.deleteSerie(params.serieId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(SerieInfoWrapper(exercise.serieList!!.toList(), false, exercise.record))
    }
}

data class GetSerieToDeleteParams(val serieId: Int)