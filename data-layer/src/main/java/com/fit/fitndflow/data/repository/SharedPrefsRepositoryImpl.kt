package com.fit.fitndflow.data.repository

import android.content.Context
import app.fit.fitndflow.domain.repository.SharedPrefsRepository
import com.fit.fitndflow.data.common.SharedPrefs


class SharedPrefsRepositoryImpl(private var mcontext: Context) : SharedPrefsRepository  {

    override fun getApiKey(): String? {
        return SharedPrefs.getApikeyFromSharedPRefs(mcontext)
    }

    override fun isNotificationShown(): Boolean {
        return SharedPrefs.get(mcontext).isNotificationShown
    }

    override fun isDontShowNotification(): Boolean {
        return SharedPrefs.get(mcontext).isDontShowNotification
    }

    override fun saveNotificationShow(boolean: Boolean) {
        SharedPrefs.get(mcontext).saveNotificationShow(boolean)
    }

    override fun saveDontShowNotification(boolean: Boolean) {
        SharedPrefs.get(mcontext).saveDontShowNotification(boolean)
    }


}