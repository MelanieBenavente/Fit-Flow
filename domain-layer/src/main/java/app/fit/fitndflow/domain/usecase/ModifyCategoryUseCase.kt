package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyCategoryUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<CategoryModelInLanguages, List<CategoryModel>>(){
    override fun run(params: CategoryModelInLanguages): Flow<List<CategoryModel>> = flow {
        val categoryModified = fitnFlowRepository.modifyCategory(params.name, params.language, params.id, params.imageUrl)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(categoryModified)
    }
}
data class CategoryModelInLanguages (val id:Int, val name: String, val language: String, val imageUrl:String)