package com.fit.fitndflow.app.domain.trainings.TrainingUseCases

import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.trainings.model.SerieInfoWrapper
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyTrainingUseCase @Inject constructor(val trainingRepository: TrainingRepository) : UseCase<ModifySerieUseCaseParams, SerieInfoWrapper>() {
    override fun run(params: ModifySerieUseCaseParams): Flow<SerieInfoWrapper> = flow {
        val exercise = trainingRepository.modifySerie(params.serieId, params.reps, params.weight)
        var isRecord = false
        params.record?.let {
            isRecord = (params.record.kg != null && params.record.reps != null) && (params.weight > params.record.kg!! || (params.weight >= params.record.kg!! && params.reps > params.record.reps!!))
        }
            emit(SerieInfoWrapper(exercise.serieList!!.toList(), isRecord, exercise.record))
    }
}
data class ModifySerieUseCaseParams(val serieId: Int, val reps: Int, val weight: Double, val record: SerieModel?)