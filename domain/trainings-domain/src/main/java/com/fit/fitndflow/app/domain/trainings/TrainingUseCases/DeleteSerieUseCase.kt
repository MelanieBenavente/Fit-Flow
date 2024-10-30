package com.fit.fitndflow.app.domain.trainings.TrainingUseCases

import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.trainings.model.SerieInfoWrapper
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
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