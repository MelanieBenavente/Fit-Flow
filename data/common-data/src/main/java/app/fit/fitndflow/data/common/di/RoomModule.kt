package app.fit.fitndflow.data.common.di

import android.content.Context
import androidx.room.Room
import app.fit.fitndflow.data.common.database.DataBase
import app.fit.fitndflow.data.common.database.migrations.DatabaseMigrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    companion object {
        const val DATABASE_NAME = "database_name"
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            Companion.DATABASE_NAME
        ).addMigrations(*DatabaseMigrations.ALL).build()

    @Singleton
    @Provides
    fun provideCategoryDao(dataBase: DataBase) = dataBase.getCategoryDao()

    @Singleton
    @Provides
    fun provideExerciseDao(dataBase: DataBase) = dataBase.getExerciseDao()

    @Singleton
    @Provides
    fun provideSerieDao(dataBase: DataBase) = dataBase.getSerieDao()

    @Singleton
    @Provides
    fun provideTrainingDao(dataBase: DataBase) = dataBase.getTrainingDao()

}
