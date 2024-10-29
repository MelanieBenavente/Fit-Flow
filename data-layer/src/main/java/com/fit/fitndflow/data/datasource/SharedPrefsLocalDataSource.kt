package com.fit.fitndflow.data.datasource

import android.content.Context
import com.fit.fitndflow.data.common.SharedPrefs

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
}