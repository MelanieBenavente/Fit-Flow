package com.fit.fitndflow.app.domain.categories.categoriesUseCases


import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MigrateToLocalUseCase @Inject constructor(private val categoriesRepository: CategoriesRepository) : UseCase<Unit, Unit>() {
    override fun run(none: Unit): Flow<Unit> = flow {
        emit(categoriesRepository.migrateToLocal())
    }
}