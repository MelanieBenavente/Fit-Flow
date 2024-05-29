package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<GetCategoryToDeleteParams, List<CategoryModel>>() {
    override fun run(params: GetCategoryToDeleteParams): Flow<List<CategoryModel>> = flow {
        val categoryToDelete = fitnFlowRepository.deleteCategory(params.categoryId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(categoryToDelete)
    }
}
data class GetCategoryToDeleteParams(val categoryId: Int)