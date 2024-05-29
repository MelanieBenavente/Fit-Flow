package com.fit.fitndflow.data.dagger

import android.content.Context
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.repository.SharedPrefsRepository
import com.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import com.fit.fitndflow.data.repository.SharedPrefsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideFitndFlowRepository(@ApplicationContext context: Context): FitnFlowRepository {
        return FitnFlowRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefsRepository(@ApplicationContext context: Context): SharedPrefsRepository {
        return SharedPrefsRepositoryImpl(context)
    }

}