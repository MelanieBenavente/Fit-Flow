package com.fit.fitndflow.app.domain.categories.categoriesUseCases

import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateInitialCategoriesAndExercisesIfNeeded @Inject constructor(private val categoriesRepository: CategoriesRepository) : UseCase<Unit, Unit>(){
    override fun run(params: Unit) = flow {
            emit(categoriesRepository.createInitialData())
    }
}