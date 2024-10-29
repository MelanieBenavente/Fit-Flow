package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieInfoWrapper
import app.fit.fitndflow.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSerieUseCase @Inject constructor(val trainingRepository: TrainingRepository): UseCase<GetSerieToDeleteParams, SerieInfoWrapper>(){
    override fun run(params: GetSerieToDeleteParams): Flow<SerieInfoWrapper> = flow {
        val exercise = trainingRepository.deleteSerie(params.serieId)
        emit(SerieInfoWrapper(exercise.serieList!!.toList(), false, exercise.record))
    }
}

data class GetSerieToDeleteParams(val serieId: Int)