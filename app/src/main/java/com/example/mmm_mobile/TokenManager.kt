package com.example.mmm_mobile

import android.content.Context
import android.content.SharedPreferences
import org.openapitools.client.infrastructure.ApiClient

class TokenManager private constructor(context: Context) {
    companion object {
        private const val tokenKey = "jwtToken"
        private const val sharedPreferencesKey = "MyPref"
        private var INSTANCE: TokenManager? = null

        fun getInstance(context: Context): TokenManager {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = TokenManager(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)

    var accessToken: String?
        get() = sharedPreferences.getString(tokenKey, null)
        set(value) {
            ApiClient.accessToken = value
            sharedPreferences.edit().putString(tokenKey, value).apply()
        }

    fun clear() {
        ApiClient.accessToken = null
        sharedPreferences.edit().remove("tokenKey").apply()
    }
}