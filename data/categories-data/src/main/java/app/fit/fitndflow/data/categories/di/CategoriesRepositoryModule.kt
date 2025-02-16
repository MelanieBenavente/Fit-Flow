package app.fit.fitndflow.data.categories.di

import android.content.Context
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface
import app.fit.fitndflow.data.categories.repositoryImpl.CategoriesRepositoryImpl
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoriesRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(@ApplicationContext context: Context, apiInterface: CategoriesApiInterface, categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource, trainingLocalDataSource : TrainingLocalDataSource): CategoriesRepository {
        return CategoriesRepositoryImpl(
            context,
            apiInterface,
            categoriesAndExercisesLocalDataSource,
            trainingLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): CategoriesApiInterface {
        return  retrofit.create(CategoriesApiInterface::class.java)
    }

}