package com.fit.fitndflow.app.domain.categories.categoriesUseCases

import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(private val categoriesRepository: CategoriesRepository) : UseCase<AddCategoryUseCaseParams, List<CategoryModel>>(){
    override fun run(params: AddCategoryUseCaseParams): Flow<List<CategoryModel>> = flow {
         val newCategory = categoriesRepository.addNewCategory(params.categoryName, params.language)
        emit(newCategory)
    }
}

data class AddCategoryUseCaseParams(val categoryName: String, val language: String)