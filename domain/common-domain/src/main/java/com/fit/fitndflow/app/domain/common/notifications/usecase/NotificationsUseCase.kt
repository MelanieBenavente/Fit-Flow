package com.fit.fitndflow.app.domain.common.notifications.usecase

import com.fit.fitndflow.app.domain.common.notifications.repository.NotificationsRepository
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