package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.data.dto.StringInLanguagesDto
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ModifyCategoryUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) : UseCase<CategoryModelInLanguages, List<CategoryModel>>(){
    override fun run(params: CategoryModelInLanguages): Flow<List<CategoryModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val categoryModified = fitnFlowRepository.modifyCategory(params.name, params.language, params.id, params.imageUrl, apiKey)
        emit(categoryModified)
    }
}
data class CategoryModelInLanguages (val id:Int, val name: String, val language: String, val imageUrl:String)