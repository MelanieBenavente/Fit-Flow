package com.fit.fitndflow.app.domain.trainings.repository

import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.repository.CommonRepository

interface TrainingRepository : CommonRepository {
    suspend fun addNewSerie(reps: Int, weight: Double, exerciseId: Int): ExerciseModel?
    suspend fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseModel?
    suspend fun deleteSerie(serieId: Int): ExerciseModel?
    suspend fun getTrainingListAndUpdateCache(date: String): List<CategoryModel>
    suspend fun updateCurrentTrainingListCache(): List<CategoryModel>
    suspend fun getSerieListOfExerciseAdded(exerciseid: Int): List<SerieModel>
}
