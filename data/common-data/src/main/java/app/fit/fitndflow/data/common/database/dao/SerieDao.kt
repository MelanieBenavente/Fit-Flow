package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM exercises_table LEFT JOIN series_table ON id = exerciseId WHERE id = :exerciseId AND date = :date")
    suspend fun  getExerciseWithSeries(exerciseId: Int, date: String): List<ExerciseWithSeriesEntity>
}