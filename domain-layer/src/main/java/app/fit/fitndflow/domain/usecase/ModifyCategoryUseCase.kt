package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyCategoryUseCase @Inject constructor(val categoriesRepository: CategoriesRepository) : UseCase<CategoryModelInLanguages, List<CategoryModel>>(){
    override fun run(params: CategoryModelInLanguages): Flow<List<CategoryModel>> = flow {
        val categoryModified = categoriesRepository.modifyCategory(params.name, params.language, params.id, params.imageUrl)
        emit(categoryModified)
    }
}
data class CategoryModelInLanguages (val id:Int, val name: String, val language: String, val imageUrl:String)