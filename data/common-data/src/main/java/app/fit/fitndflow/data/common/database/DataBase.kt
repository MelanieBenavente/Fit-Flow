package app.fit.fitndflow.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import app.fit.fitndflow.data.common.database.entities.SeriesEntity

@Database(entities = [CategoryEntity::class, ExerciseEntity::class, SeriesEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun getCategoryDao() : CategoryDao
}