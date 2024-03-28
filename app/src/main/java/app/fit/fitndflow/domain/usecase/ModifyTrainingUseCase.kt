package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyTrainingUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<ModifySerieUseCaseParams, List<SerieModel>>() {
    override fun run(params: ModifySerieUseCaseParams): Flow<List<SerieModel>> = flow {
        val series = fitnFlowRepository.modifySerie(params.serieId, params.reps, params.weight)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(series)
    }
}
data class ModifySerieUseCaseParams(val serieId: Int, val reps: Int, val weight: Double)