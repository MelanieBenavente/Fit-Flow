package app.fit.fitndflow.data.trainings.datasource.remote

import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.ExerciseDto
import app.fit.fitndflow.data.common.dto.SerieDto
import app.fit.fitndflow.data.common.model.ExcepcionApi
import app.fit.fitndflow.data.trainings.datasource.TrainingDataSourceInterface
import app.fit.fitndflow.data.trainings.dto.AddSerieRequestDto
import app.fit.fitndflow.data.trainings.dto.SerieForAddSerieRequestDto
import app.fit.fitndflow.data.trainings.model.TrainingsApiInterface

class TrainingRemoteDataSource(private val trainingApiInterface: TrainingsApiInterface) : TrainingDataSourceInterface {
    override fun addNewSerie(reps: Int, weight: Double, exerciseId: Int, date: String): ExerciseDto? {
        val serieForAddSerieRequestDto = SerieForAddSerieRequestDto(
            reps,
            weight,
            ExerciseDto(exerciseId, null, null, null, null)
        )
        val addSerieRequestDto =
            AddSerieRequestDto(date, serieForAddSerieRequestDto)
        val response = trainingApiInterface.addNewSerie(addSerieRequestDto).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()
    }

    override fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseDto? {
        val serieDto = SerieDto(serieId, reps, weight)
        val response = trainingApiInterface.modifySerie(serieDto).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()
    }

    override fun deleteSerie(serieId: Int): ExerciseDto? {
        val response = trainingApiInterface.deleteSerie(serieId).execute()
        if (response != null && !response.isSuccessful) {
            throw ExcepcionApi(response.code())
        }
        return response.body()
    }

    override fun getTrainingListAndUpdateCache(date: String): List<CategoryDto> {
        val response = trainingApiInterface.getCategoriesAndTrainings(date).execute()
        return response.body()?.toList().orEmpty()
    }
}