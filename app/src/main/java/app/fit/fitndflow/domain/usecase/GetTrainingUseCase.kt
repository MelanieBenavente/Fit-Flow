package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTrainingUseCase(val fitnFlowRepository: FitnFlowRepository, val context: Context) :
    UseCase<GetTrainingUseCaseParams, List<CategoryModel>>() {
    override fun run(params: GetTrainingUseCaseParams): Flow<List<CategoryModel>> = flow {
        val apiKey = SharedPrefs.getApikeyFromSharedPRefs(context)
        val getTrainingByDate = fitnFlowRepository.getTrainingList(params.date, apiKey)
        fitnFlowRepository.updateCurrentTrainingListCache(apiKey)
        emit(getTrainingByDate)
    }
}
data class GetTrainingUseCaseParams(val date: String)