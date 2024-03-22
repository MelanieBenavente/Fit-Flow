package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteCategoryUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<GetCategoryToDeleteParams, List<CategoryModel>>() {
    override fun run(params: GetCategoryToDeleteParams): Flow<List<CategoryModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val categoryToDelete = fitnFlowRepository.deleteCategory(params.categoryId, apiKey)
        emit(categoryToDelete)
    }
}
data class GetCategoryToDeleteParams(val categoryId: Int)