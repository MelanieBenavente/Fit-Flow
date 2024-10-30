package app.fit.fitndflow.data.categories.di

import android.content.Context
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface
import app.fit.fitndflow.data.categories.repositoryImpl.CategoriesRepositoryImpl
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.common.model.ApiInterface
import app.fit.fitndflow.data.common.model.HeaderInterceptor
import app.fit.fitndflow.domain.repository.CategoriesRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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