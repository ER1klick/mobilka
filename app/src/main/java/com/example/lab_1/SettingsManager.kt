package com.example.lab_1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(context: Context) {

    private val dataStore = context.dataStore
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
        const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE_KEY] ?: false
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_KEY] = isDarkMode
        }
    }

    fun getNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY, false)
    }

    fun setNotificationsEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(NOTIFICATIONS_ENABLED_KEY, isEnabled).apply()
    }
}