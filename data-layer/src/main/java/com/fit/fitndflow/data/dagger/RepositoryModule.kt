package com.fit.fitndflow.data.dagger

import android.content.Context
import app.fit.fitndflow.domain.repository.CategoriesRepository
import app.fit.fitndflow.domain.repository.ExercisesRepository
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.repository.SharedPrefsRepository
import app.fit.fitndflow.domain.repository.TrainingRepository
import com.fit.fitndflow.data.common.ApiInterface
import com.fit.fitndflow.data.common.HeaderInterceptor
import com.fit.fitndflow.data.datasource.CategoriesAndExercisesLocalDataSource
import com.fit.fitndflow.data.datasource.TrainingLocalDataSource
import com.fit.fitndflow.data.repository.CategoriesRepositoryImpl
import com.fit.fitndflow.data.repository.ExercisesRepositoryImpl
import com.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import com.fit.fitndflow.data.repository.SharedPrefsRepositoryImpl
import com.fit.fitndflow.data.repository.TrainingRepositoryImpl
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
class RepositoryModule {
    @Provides
    @Singleton
    fun provideFitndFlowRepository(@ApplicationContext context: Context, apiInterface: ApiInterface): FitnFlowRepository {
        return FitnFlowRepositoryImpl(context, apiInterface)
    }
    @Provides
    @Singleton
    fun provideCategoriesRepository(@ApplicationContext context: Context, apiInterface: ApiInterface, categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource, trainingLocalDataSource : TrainingLocalDataSource): CategoriesRepository {
        return CategoriesRepositoryImpl(context, apiInterface, categoriesAndExercisesLocalDataSource, trainingLocalDataSource)
    }
    @Provides
    @Singleton
    fun provideExercisesRepository(@ApplicationContext context: Context, apiInterface: ApiInterface, categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource, trainingLocalDataSource : TrainingLocalDataSource): ExercisesRepository {
        return ExercisesRepositoryImpl(context, apiInterface, categoriesAndExercisesLocalDataSource, trainingLocalDataSource)
    }
    @Provides
    @Singleton
    fun provideTrainingRepository(@ApplicationContext context: Context, apiInterface: ApiInterface, trainingLocalDataSource : TrainingLocalDataSource): TrainingRepository {
        return TrainingRepositoryImpl(context, apiInterface, trainingLocalDataSource)
    }
    @Provides
    @Singleton
    fun provideSharedPrefsRepository(@ApplicationContext context: Context): SharedPrefsRepository {
        return SharedPrefsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return  retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesTrainingLocalDataSource() : TrainingLocalDataSource {
        return TrainingLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideCategoriesAndExercisesLocalDataSource() : CategoriesAndExercisesLocalDataSource {
        return CategoriesAndExercisesLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(ApiInterface.URL_BASE).client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun provideClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(@ApplicationContext context: Context): HeaderInterceptor {
        return HeaderInterceptor(context)
    }

}