package app.fit.fitndflow.data.trainings.di

import android.content.Context
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.dao.SerieDao
import app.fit.fitndflow.data.common.database.dao.TrainingDao
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import app.fit.fitndflow.data.trainings.datasource.remote.TrainingRemoteDataSource
import app.fit.fitndflow.data.trainings.model.TrainingsApiInterface
import app.fit.fitndflow.data.trainings.repositoryImpl.TrainingRepositoryImpl
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrainingsRepositoryModule {

    @Provides
    @Singleton
    fun provideTrainingRepository(
        @ApplicationContext context: Context,
        trainingRemoteDataSource: TrainingRemoteDataSource,
        trainingCacheLocalDataSource: TrainingCacheLocalDataSource,
        serieDao: SerieDao,
        exerciseDao: ExerciseDao,
        trainingDao: TrainingDao,
        sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
    ): TrainingRepository {
        return TrainingRepositoryImpl(context, trainingRemoteDataSource, trainingCacheLocalDataSource, serieDao, trainingDao, exerciseDao, sharedPrefsLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideTrainingRemoteDataSource(trainingsApiInterface: TrainingsApiInterface): TrainingRemoteDataSource {
        return TrainingRemoteDataSource(trainingsApiInterface)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): TrainingsApiInterface {
        return retrofit.create(TrainingsApiInterface::class.java)
    }
}