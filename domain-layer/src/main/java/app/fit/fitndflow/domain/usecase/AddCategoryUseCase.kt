package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<AddCategoryUseCaseParams, List<CategoryModel>>(){
    override fun run(params: AddCategoryUseCaseParams): Flow<List<CategoryModel>> = flow {
         val newCategory = fitnFlowRepository.addNewCategory(params.categoryName, params.language)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(newCategory)
    }
}

data class AddCategoryUseCaseParams(val categoryName: String, val language: String)