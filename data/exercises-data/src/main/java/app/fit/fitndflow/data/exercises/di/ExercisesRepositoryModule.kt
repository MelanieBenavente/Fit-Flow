package app.fit.fitndflow.data.exercises.di

import android.content.Context
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.exercises.model.ExercisesApiInterface
import app.fit.fitndflow.data.exercises.repositoryImpl.ExercisesRepositoryImpl
import app.fit.fitndflow.domain.repository.ExercisesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExercisesRepositoryModule {
    @Provides
    @Singleton
    fun provideExercisesRepository(@ApplicationContext context: Context, apiInterface: ExercisesApiInterface, categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource, trainingLocalDataSource : TrainingLocalDataSource): ExercisesRepository {
        return ExercisesRepositoryImpl(context, apiInterface, categoriesAndExercisesLocalDataSource, trainingLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ExercisesApiInterface {
        return  retrofit.create(ExercisesApiInterface::class.java)
    }
}