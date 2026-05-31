package com.fit.fitndflow.app.domain.trainings.TrainingUseCases

import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrainingUseCase @Inject constructor(private val trainingRepository: TrainingRepository) :
    UseCase<GetTrainingUseCaseParams, List<CategoryModel>>() {
    override fun run(params: GetTrainingUseCaseParams): Flow<List<CategoryModel>> = flow {
        val getTrainingByDate = trainingRepository.getTrainingListAndUpdateCache(params.date)
        trainingRepository.updateCurrentTrainingListCache()
        emit(getTrainingByDate)
    }
}
data class GetTrainingUseCaseParams(val date: String)