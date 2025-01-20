package com.example.aplicationbank.data.local

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val TOKEN_EXPIRY_KEY = stringPreferencesKey("token_expiry")
        private val LAST_VALIDATED_TIME_KEY = stringPreferencesKey("last_validated_time")  // Nueva clave
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveTokens(accessToken: String, refreshToken: String, expiryTime: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
            preferences[TOKEN_EXPIRY_KEY] = expiryTime
            preferences[LAST_VALIDATED_TIME_KEY] = Instant.now().toString()
        }
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun isSessionValid(): Flow<Boolean> {
//        return context.dataStore.data.map { preferences ->
//            val expiryTime = preferences[TOKEN_EXPIRY_KEY]?.let { Instant.parse(it) }
//            val artificialExpiryTime = Instant.now().plusSeconds(2 * 60)
//            (expiryTime?.isAfter(Instant.now()) ?: false) && Instant.now().isBefore(artificialExpiryTime)
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isSessionValid(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            val lastValidatedTime = preferences[LAST_VALIDATED_TIME_KEY]?.let { Instant.parse(it) }

            if (lastValidatedTime == null) {
                return@map false
            }

            val currentTime = Instant.now()
            val twoMinutesLater = lastValidatedTime.plusSeconds(2 * 60)
            currentTime.isBefore(twoMinutesLater)
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
