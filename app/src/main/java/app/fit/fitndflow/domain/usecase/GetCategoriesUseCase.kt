package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(val context: Context, val fitnFlowRepository: FitnFlowRepository) : UseCase<Unit, List<CategoryModel>>() {
    override fun run(params: Unit): Flow<List<CategoryModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val categories = fitnFlowRepository.getCategoryList(apiKey)
        emit(categories)
    }
}