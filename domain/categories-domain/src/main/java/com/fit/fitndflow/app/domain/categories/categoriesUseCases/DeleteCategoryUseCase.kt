package com.fit.fitndflow.app.domain.categories.categoriesUseCases

import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val categoriesRepository: CategoriesRepository) : UseCase<GetCategoryToDeleteParams, List<CategoryModel>>() {
     override fun run(params: GetCategoryToDeleteParams): Flow<List<CategoryModel>> = flow {
        val categoryList = categoriesRepository.deleteCategory(params.categoryId)
        emit(categoryList)
    }
}
data class GetCategoryToDeleteParams(val categoryId: Int)