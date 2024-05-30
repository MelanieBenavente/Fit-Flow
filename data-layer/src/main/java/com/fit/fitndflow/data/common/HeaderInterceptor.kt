package com.fit.fitndflow.data.common

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val context: Context) : Interceptor {
    private val KEY: String = "auth-token"
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header(KEY, SharedPrefs.getApikeyFromSharedPRefs(context).orEmpty()).build()
        val response = chain.proceed(request)
        return response
    }

}