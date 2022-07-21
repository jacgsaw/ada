package com.jacgsaw.adda.storage

import android.content.SharedPreferences
import com.jacgsaw.adda.utils.TOKEN_KEY

class SharedPreferencesLocalStorage(private val sharedPreferences: SharedPreferences) :
    TokenStorage {
    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}