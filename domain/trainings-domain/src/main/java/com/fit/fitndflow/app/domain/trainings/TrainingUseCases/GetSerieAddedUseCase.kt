package com.fit.fitndflow.app.domain.trainings.TrainingUseCases

import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.usecase.UseCase
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSerieAddedUseCase @Inject constructor(private val trainingRepository: TrainingRepository) : UseCase<GetSerieAddedParam, List<SerieModel>>(){
    override fun run(params: GetSerieAddedParam): Flow<List<SerieModel>> = flow {
        val serieAdded = trainingRepository.getSerieListOfExerciseAdded(params.exerciseId)
        emit(serieAdded)
    }
}
data class GetSerieAddedParam(val exerciseId: Int)