package app.fit.fitndflow.data.common.di

import android.content.Context
import androidx.room.Room
import app.fit.fitndflow.data.common.database.DataBase
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
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, DataBase::class.java,
        Companion.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCategoryDao(dataBase: DataBase) = dataBase.getCategoryDao()
}