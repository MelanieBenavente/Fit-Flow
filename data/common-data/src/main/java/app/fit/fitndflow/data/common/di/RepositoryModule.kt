package app.fit.fitndflow.data.common.di

import android.content.Context
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.common.model.ApiInterface
import app.fit.fitndflow.data.common.model.HeaderInterceptor
import app.fit.fitndflow.data.common.notifications.repository.NotificationsRepositoryImpl
import app.fit.fitndflow.domain.repository.NotificationsRepository
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
    fun provideNotificationsRepository(sharedPrefsLocalDataSource: SharedPrefsLocalDataSource): NotificationsRepository {
        return NotificationsRepositoryImpl(sharedPrefsLocalDataSource)
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
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return  retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesSharedPrefsLocalDataSource(@ApplicationContext context: Context) : SharedPrefsLocalDataSource {
        return SharedPrefsLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(app.fit.fitndflow.data.common.model.ApiInterface.URL_BASE).client(client)
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