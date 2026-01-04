package app.fit.fitndflow.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.dao.SerieDao
import app.fit.fitndflow.data.common.database.dao.TrainingDao
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import app.fit.fitndflow.data.common.database.entities.SerieEntity

@Database(entities = [CategoryEntity::class, ExerciseEntity::class, SerieEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun getCategoryDao() : CategoryDao

    abstract fun getExerciseDao() : ExerciseDao

    abstract fun getSerieDao() : SerieDao

    abstract fun getTrainingDao() : TrainingDao
}