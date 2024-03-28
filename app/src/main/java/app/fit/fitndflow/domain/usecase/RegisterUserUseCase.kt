package app.fit.fitndflow.domain.usecase

import android.content.Context
import app.fit.fitndflow.data.common.SharedPrefs
import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository) : UseCase<RegisterUserUseCaseParams, UserModel>() {
    override fun run(params: RegisterUserUseCaseParams): Flow<UserModel> = flow {
        val userRegisterResponse = fitnFlowRepository.registerUser(params.userName, params.email, params.premium)
        emit(userRegisterResponse)
    }
}

data class RegisterUserUseCaseParams(val userName: String?, val email: String?, val premium: String?)