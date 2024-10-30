package app.fit.fitndflow.data.user.di

import android.content.Context
import app.fit.fitndflow.data.common.datasource.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.user.model.UserApiInterface
import app.fit.fitndflow.data.user.repositoryImpl.RegisterUserRepositoryImpl
import app.fit.fitndflow.domain.repository.RegisterUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserRepositoryModule {

    @Provides
    @Singleton
    fun provideRegisterUserRepository(@ApplicationContext context: Context, apiInterface: UserApiInterface, sharedPrefsLocalDataSource: SharedPrefsLocalDataSource): RegisterUserRepository {
        return RegisterUserRepositoryImpl(context, apiInterface, sharedPrefsLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): UserApiInterface {
        return  retrofit.create(UserApiInterface::class.java)
    }

}