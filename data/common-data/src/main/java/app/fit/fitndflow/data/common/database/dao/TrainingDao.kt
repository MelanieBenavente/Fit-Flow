package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import app.fit.fitndflow.data.common.database.entities.CategoryWithExercisesAndSeriesEntity

@Dao
interface TrainingDao {

    @Transaction
    @Query("SELECT * FROM categories_table")
    suspend fun getFullTraining(): List<CategoryWithExercisesAndSeriesEntity>
}