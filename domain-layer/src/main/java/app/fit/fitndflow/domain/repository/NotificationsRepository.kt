package app.fit.fitndflow.domain.repository

import app.fit.fitndflow.domain.common.repository.CommonRepository

interface NotificationsRepository : CommonRepository {

    fun isNotificationShown(): Boolean

    fun isDontShowNotification(): Boolean

    fun saveNotificationShow(boolean: Boolean)

    fun saveDontShowNotification(boolean: Boolean)
}