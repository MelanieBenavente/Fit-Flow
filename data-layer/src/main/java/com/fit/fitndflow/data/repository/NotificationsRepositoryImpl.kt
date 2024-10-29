package com.fit.fitndflow.data.repository

import app.fit.fitndflow.domain.repository.NotificationsRepository
import com.fit.fitndflow.data.datasource.SharedPrefsLocalDataSource


class NotificationsRepositoryImpl(private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource) : NotificationsRepository  {

    override fun isNotificationShown(): Boolean {
        return sharedPrefsLocalDataSource.isNotificationShown()
    }

    override fun isDontShowNotification(): Boolean {
        return sharedPrefsLocalDataSource.isDontShowNotification()
    }

    override fun saveNotificationShow(boolean: Boolean) {
        sharedPrefsLocalDataSource.saveNotificationShow(boolean)
    }

    override fun saveDontShowNotification(boolean: Boolean) {
        sharedPrefsLocalDataSource.saveDontShowNotification(boolean)    }
}