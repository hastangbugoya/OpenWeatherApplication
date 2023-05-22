package com.example.openweatherapplication.model

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class MySharedPreference(context: Context) {
    private val masterKeyAlias  = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        "my_secure_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCity(key : String?) {
        pref.edit().putString("lastcity", key?.trim() ?: "no key").apply()
    }

    fun getCity() : String? = pref.getString("lastcity", "")


    fun clearData() {
        pref.edit().clear().apply()
    }

    companion object {
        private var instance: MySharedPreference? = null

        fun getInstance(context: Context): MySharedPreference {
            if (instance == null) {
                instance = MySharedPreference(context)
            }
            return instance!!
        }
    }
}