package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<Unit, List<CategoryModel>>() {
    override fun run(params: Unit): Flow<List<CategoryModel>> = flow {
        val categories = fitnFlowRepository.getCategoryList()
        emit(categories)
    }
}