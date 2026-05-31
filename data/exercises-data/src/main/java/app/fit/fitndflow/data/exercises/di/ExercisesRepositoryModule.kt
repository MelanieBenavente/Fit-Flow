package app.fit.fitndflow.data.exercises.di

import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.datasource.local.CategoriesAndExercisesCacheLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import app.fit.fitndflow.data.exercises.datasource.remote.ExerciseRemoteDataSource
import app.fit.fitndflow.data.exercises.model.ExercisesApiInterface
import app.fit.fitndflow.data.exercises.repositoryImpl.ExercisesRepositoryImpl
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExercisesRepositoryModule {
    @Provides
    @Singleton
    fun provideExercisesRepository(
        exerciseRemoteDataSource: ExerciseRemoteDataSource,
        categoriesAndExercisesCacheLocalDataSource: CategoriesAndExercisesCacheLocalDataSource,
        trainingCacheLocalDataSource: TrainingCacheLocalDataSource,
        exerciseDao: ExerciseDao,
        sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
    ): ExercisesRepository {
        return ExercisesRepositoryImpl(
            exerciseRemoteDataSource,
            exerciseDao,
            categoriesAndExercisesCacheLocalDataSource,
            trainingCacheLocalDataSource,
            sharedPrefsLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideExerciseRemoteDataSource(exerciseApiInterface: ExercisesApiInterface): ExerciseRemoteDataSource {
        return ExerciseRemoteDataSource(exerciseApiInterface)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ExercisesApiInterface {
        return retrofit.create(ExercisesApiInterface::class.java)
    }
}