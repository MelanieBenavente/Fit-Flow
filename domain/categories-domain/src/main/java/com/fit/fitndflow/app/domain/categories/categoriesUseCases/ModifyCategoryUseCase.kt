package com.fit.fitndflow.app.domain.categories.categoriesUseCases

import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
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