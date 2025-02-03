package com.fit.fitndflow.app.domain.trainings.repository

import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.repository.CommonRepository

interface TrainingRepository : CommonRepository {
    @Throws(Exception::class)
    fun addNewSerie(reps: Int, weight: Double, exerciseId: Int): ExerciseModel?
    @Throws(Exception::class)
    fun modifySerie(serieId: Int, reps: Int, weight: Double): ExerciseModel?
    @Throws(Exception::class)
    fun deleteSerie(serieId: Int): ExerciseModel?
    @Throws(Exception::class)
    fun getTrainingListAndUpdateCache(date: String?): List<CategoryModel?>?
    @Throws(Exception::class)
    fun updateCurrentTrainingListCache(): List<CategoryModel?>?
    @Throws(Exception::class)
    fun getSerieListOfExerciseAdded(exerciseid: Int): List<SerieModel?>?
}
