package app.fit.fitndflow.data.common.datasource

import android.content.Context
import app.fit.fitndflow.data.common.model.SharedPrefs

class SharedPrefsLocalDataSource(private var mcontext: Context) {

    fun getApiKey(): String? {
        return SharedPrefs.getApikeyFromSharedPRefs(mcontext)
    }
    fun saveApiKey(apiKey: String) {
        SharedPrefs.saveApikeyToSharedPRefs(mcontext, apiKey)
    }
    fun isNotificationShown(): Boolean {
        return SharedPrefs.get(mcontext).isNotificationShown
    }
    fun isDontShowNotification(): Boolean {
        return SharedPrefs.get(mcontext).isDontShowNotification
    }
    fun saveNotificationShow(boolean: Boolean) {
        SharedPrefs.get(mcontext).saveNotificationShow(boolean)
    }

    fun saveDontShowNotification(boolean: Boolean) {
        SharedPrefs.get(mcontext).saveDontShowNotification(boolean)
    }

    fun saveInitialDataCreated() {
        SharedPrefs.get(mcontext).saveInitialDataCreated(true)
    }

    fun getIsInitialDataCreated() = SharedPrefs.get(mcontext).isInitialDataCreated

    fun saveIsLocal() {
        SharedPrefs.get(mcontext).saveLocal(true)
    }

    fun getIsLocal() = SharedPrefs.get(mcontext).isLocal
}