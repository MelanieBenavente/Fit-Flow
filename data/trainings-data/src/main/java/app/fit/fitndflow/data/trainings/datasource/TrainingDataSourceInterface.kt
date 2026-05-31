package app.fit.fitndflow.data.trainings.datasource

import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.ExerciseDto

interface TrainingDataSourceInterface {

    fun addNewSerie(reps: Int, weight: Double, exerciseId: Int, date: String) : ExerciseDto?

    fun modifySerie(serieId: Int, reps: Int, weight: Double) : ExerciseDto?

    fun deleteSerie(serieId: Int) : ExerciseDto?

    fun getTrainingListAndUpdateCache(date: String) : List<CategoryDto>
}