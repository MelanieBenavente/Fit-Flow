package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieInfoWrapper
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyTrainingUseCase @Inject constructor(val trainingRepository: TrainingRepository) : UseCase<ModifySerieUseCaseParams, SerieInfoWrapper>() {
    override fun run(params: ModifySerieUseCaseParams): Flow<SerieInfoWrapper> = flow {
        val exercise = trainingRepository.modifySerie(params.serieId, params.reps, params.weight)
        var isRecord = false
        params.record?.let {
            isRecord = (params.record.kg != null && params.record.reps != null) && (params.weight > params.record.kg || (params.weight >= params.record.kg && params.reps > params.record.reps))
        }
            emit(SerieInfoWrapper(exercise.serieList!!.toList(), isRecord, exercise.record))
    }
}
data class ModifySerieUseCaseParams(val serieId: Int, val reps: Int, val weight: Double, val record: SerieModel?)