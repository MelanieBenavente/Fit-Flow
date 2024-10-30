package app.fit.fitndflow.data.common.notifications.repository

import app.fit.fitndflow.data.common.datasource.SharedPrefsLocalDataSource
import com.fit.fitndflow.app.domain.common.notifications.repository.NotificationsRepository

class NotificationsRepositoryImpl(private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource) :
    NotificationsRepository {

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