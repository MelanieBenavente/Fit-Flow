package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.repository.SharedPrefsRepository
import javax.inject.Inject

class SharedPrefsUseCase @Inject constructor(val sharedPrefsRepository: SharedPrefsRepository){
    fun isUserRegistered(): Boolean {
        return sharedPrefsRepository.getApiKey() != null
    }

    fun isNotificationShown(): Boolean {
        return sharedPrefsRepository.isNotificationShown()
    }

    fun isDonShowNotification(): Boolean {
        return sharedPrefsRepository.isDontShowNotification()
    }

    fun saveNotificationShow(boolean: Boolean) {
        sharedPrefsRepository.saveNotificationShow(boolean)
    }

    fun saveDontShowNotification(boolean: Boolean) {
        sharedPrefsRepository.saveDontShowNotification(boolean)
    }
}