package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.repository.NotificationsRepository
import javax.inject.Inject

class NotificationsUseCase @Inject constructor(private val notificationsRepository: NotificationsRepository){

    fun isNotificationShown(): Boolean {
        return notificationsRepository.isNotificationShown()
    }

    fun isDonShowNotification(): Boolean {
        return notificationsRepository.isDontShowNotification()
    }

    fun saveNotificationShow(boolean: Boolean) {
        notificationsRepository.saveNotificationShow(boolean)
    }

    fun saveDontShowNotification(boolean: Boolean) {
        notificationsRepository.saveDontShowNotification(boolean)
    }
}