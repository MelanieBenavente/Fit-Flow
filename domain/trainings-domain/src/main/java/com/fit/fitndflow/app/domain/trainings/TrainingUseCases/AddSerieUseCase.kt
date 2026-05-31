package com.fit.fitndflow.app.domain.trainings.TrainingUseCases

import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.trainings.model.SerieInfoWrapper
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AddSerieUseCase @Inject constructor(private val trainingRepository: TrainingRepository) : UseCase<AddSerieUseCaseParams, SerieInfoWrapper>(){
    override fun run(params: AddSerieUseCaseParams): Flow<SerieInfoWrapper> = flow {
        val exercise = trainingRepository.addNewSerie(params.reps, params.weight, params.exerciseId)
        var isRecord = false
        params.record?.let {
            isRecord = (params.record.kg != null && params.record.reps != null) && (params.weight > params.record.kg!! || (params.weight >= params.record.kg!! && params.reps > params.record.reps!!))
        }
        emit(SerieInfoWrapper(exercise?.serieList?.toList() ?: emptyList(), isRecord, exercise?.record))
    }
}

data class AddSerieUseCaseParams(val reps: Int, val weight: Double, val exerciseId: Int, val record: SerieModel?)