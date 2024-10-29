package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.repository.RegisterUserRepository
import javax.inject.Inject

class GetIsUserRegisteredUseCase @Inject constructor(private val registerUserRepository: RegisterUserRepository){
    fun isUserRegistered(): Boolean {
        return registerUserRepository.apikey != null
    }
}