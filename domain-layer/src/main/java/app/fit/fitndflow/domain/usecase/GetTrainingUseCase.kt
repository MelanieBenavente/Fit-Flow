package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrainingUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) :
    UseCase<GetTrainingUseCaseParams, List<CategoryModel>>() {
    override fun run(params: GetTrainingUseCaseParams): Flow<List<CategoryModel>> = flow {
        val getTrainingByDate = fitnFlowRepository.getTrainingList(params.date)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(getTrainingByDate)
    }
}
data class GetTrainingUseCaseParams(val date: String)