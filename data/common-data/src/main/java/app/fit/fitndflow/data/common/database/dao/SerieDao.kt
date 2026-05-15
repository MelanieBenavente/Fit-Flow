package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.fit.fitndflow.data.common.database.entities.ExerciseSerieFlat
import app.fit.fitndflow.data.common.database.entities.ExerciseWithSeriesEntity
import app.fit.fitndflow.data.common.database.entities.SerieEntity

@Dao
interface SerieDao {
    @Query("SELECT * FROM series_table WHERE exerciseId = :exerciseId")
    suspend fun getAllSeriesByExercise(exerciseId: Int): List<SerieEntity>

    @Query("SELECT * FROM series_table WHERE id = :serieId")
    suspend fun getSerie(serieId: Int): SerieEntity

    @Query("SELECT * FROM series_table WHERE date = :date")
    suspend fun getSeriesByDate(date: String): List<SerieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    @Query("UPDATE series_table SET reps = :reps, weight = :weight WHERE id = :serieId")
    suspend fun updateSerie(serieId: Int, reps: Int?, weight: Double?)

    @Query("DELETE FROM series_table WHERE id = :serieId")
    suspend fun deleteSerie(serieId: Int)

    @Query("SELECT * FROM exercises_table LEFT JOIN series_table ON exercises_table.id = exerciseId WHERE exercises_table.id = :exerciseId AND date = :date")
    suspend fun  getExerciseWithSeries(exerciseId: Int, date: String): List<ExerciseWithSeriesEntity>

    @Query("""
    SELECT 
        e.id AS exerciseId,
        e.nameEn AS exerciseNameEn,
        e.nameEs AS exerciseNameEs,
        e.firstReps AS firstReps,
        e.firstWeight AS firstWeight,
        s.reps,
        s.weight,
        s.date
    FROM exercises_table e
    INNER JOIN series_table s ON e.id = s.exerciseId
    WHERE e.id = :exerciseId AND s.date = :date
""")
    suspend fun getExerciseWithSeriesByDate(
        exerciseId: Int,
        date: String
    ): List<ExerciseSerieFlat>
}
