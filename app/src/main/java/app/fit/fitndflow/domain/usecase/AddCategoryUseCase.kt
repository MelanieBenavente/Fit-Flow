package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddCategoryUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<AddCategoryUseCaseParams, List<CategoryModel>>(){
    override fun run(params: AddCategoryUseCaseParams): Flow<List<CategoryModel>> = flow {
         val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
         val newCategory = fitnFlowRepository.addNewCategory(params.categoryName, params.language, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(newCategory)
    }
}

data class AddCategoryUseCaseParams(val categoryName: String, val language: String)