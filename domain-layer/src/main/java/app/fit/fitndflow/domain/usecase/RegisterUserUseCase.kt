package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.RegisterUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val registerUserRepository: RegisterUserRepository) : UseCase<RegisterUserUseCaseParams, UserModel>() {
    override fun run(params: RegisterUserUseCaseParams): Flow<UserModel> = flow {
        val userRegisterResponse = registerUserRepository.registerUser(params.userName, params.email, params.premium)
        emit(userRegisterResponse)
    }
}

data class RegisterUserUseCaseParams(val userName: String? = null, val email: String? = null, val premium: String? = null)