package app.fit.fitndflow.data.categories.di

import app.fit.fitndflow.data.categories.datasource.local.InitialExercisesCreatorHelper
import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface
import app.fit.fitndflow.data.categories.repositoryImpl.CategoriesRepositoryImpl
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.datasource.local.CategoriesAndExercisesCacheLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoriesRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(categoryRemoteDataSource: CategoryRemoteDataSource, exerciseDao: ExerciseDao, categoriesAndExercisesCacheLocalDataSource: CategoriesAndExercisesCacheLocalDataSource, trainingCacheLocalDataSource : TrainingCacheLocalDataSource, categoryDao: CategoryDao, initialExercisesCreatorHelper: InitialExercisesCreatorHelper, sharedPrefsLocalDataSource: SharedPrefsLocalDataSource
    ): CategoriesRepository {
        return CategoriesRepositoryImpl(
            categoryRemoteDataSource,
            categoryDao,
            exerciseDao,
            categoriesAndExercisesCacheLocalDataSource,
            trainingCacheLocalDataSource,
            initialExercisesCreatorHelper,
            sharedPrefsLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideInitialExercisesCreatorHelper(categoryDao: CategoryDao, exerciseDao: ExerciseDao): InitialExercisesCreatorHelper = InitialExercisesCreatorHelper(categoryDao = categoryDao, exerciseDao = exerciseDao)

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