package com.fit.fitndflow.app.domain.user.userUseCases

import com.fit.fitndflow.app.domain.user.repository.RegisterUserRepository
import javax.inject.Inject

class GetIsUserRegisteredUseCase @Inject constructor(private val registerUserRepository: RegisterUserRepository){
    fun isUserRegistered(): Boolean {
        return registerUserRepository.apikey != null
    }
}