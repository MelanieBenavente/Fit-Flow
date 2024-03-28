package app.fit.fitndflow.domain.usecase


import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AddSerieUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<AddSerieUseCaseParams, List<SerieModel>>(){
    override fun run(params: AddSerieUseCaseParams): Flow<List<SerieModel>> = flow {
        val series = fitnFlowRepository.addNewSerie(params.reps, params.weight, params.exerciseId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(series)
    }
}

data class AddSerieUseCaseParams(val reps: Int, val weight: Double, val exerciseId: Int)