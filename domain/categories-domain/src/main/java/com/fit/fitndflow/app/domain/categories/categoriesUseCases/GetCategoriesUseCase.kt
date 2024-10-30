package com.fit.fitndflow.app.domain.categories.categoriesUseCases


import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(val categoriesRepository: CategoriesRepository) : UseCase<Unit, List<CategoryModel>>() {
    override fun run(params: Unit): Flow<List<CategoryModel>> = flow {
        val categories = categoriesRepository.getCategoryList()
        emit(categories)
    }
}