package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises_table WHERE categoryId = :categoryId")
    suspend fun getAllExercisesByCategory(categoryId: Int): List<ExerciseEntity>

    @Query("SELECT * FROM exercises_table WHERE id = :exerciseId")
    suspend fun getExercise(exerciseId: Int): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercise: List<ExerciseEntity>)

    @Query("UPDATE exercises_table SET nameEs = :nameEs, nameEn = :nameEn, categoryId = :categoryId WHERE id = :exerciseId")
    suspend fun updateExercise(exerciseId: Int, nameEs: String, nameEn: String, categoryId: Int)

    @Query("DELETE FROM exercises_table WHERE id = :exerciseId")
    suspend fun deleteExercise(exerciseId: Int)


}