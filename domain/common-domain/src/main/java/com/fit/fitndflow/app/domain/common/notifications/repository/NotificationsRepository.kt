package com.fit.fitndflow.app.domain.common.notifications.repository

import com.fit.fitndflow.app.domain.common.repository.CommonRepository


interface NotificationsRepository : CommonRepository {

    fun isNotificationShown(): Boolean

    fun isDontShowNotification(): Boolean

    fun saveNotificationShow(boolean: Boolean)

    fun saveDontShowNotification(boolean: Boolean)
}