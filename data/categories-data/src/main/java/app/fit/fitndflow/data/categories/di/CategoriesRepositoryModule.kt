package app.fit.fitndflow.data.categories.di

import android.content.Context
import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface
import app.fit.fitndflow.data.categories.repositoryImpl.CategoriesRepositoryImpl
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesCacheLocalDataSource
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
    fun provideCategoriesRepository(@ApplicationContext context: Context, categoryRemoteDataSource: CategoryRemoteDataSource, categoriesAndExercisesCacheLocalDataSource: CategoriesAndExercisesCacheLocalDataSource, trainingLocalDataSource : TrainingLocalDataSource, categoryDao: CategoryDao): CategoriesRepository {
        return CategoriesRepositoryImpl(
            context,
            categoryRemoteDataSource,
            categoryDao,
            categoriesAndExercisesCacheLocalDataSource,
            trainingLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(categoryApiInterface: CategoriesApiInterface): CategoryRemoteDataSource {
        return CategoryRemoteDataSource(categoryApiInterface)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): CategoriesApiInterface {
        return  retrofit.create(CategoriesApiInterface::class.java)
    }
}