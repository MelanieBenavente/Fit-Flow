package app.fit.fitndflow.dagger

import android.content.Context
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.repository.FitnFlowRepository
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

}