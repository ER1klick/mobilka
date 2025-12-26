@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
package com.example.lab_1

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi

data class BackupState(
    val externalFileExists: Boolean = false,
    val externalFilePath: String = "N/A",
    val internalFileExists: Boolean = false
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsManager = SettingsManager(application)
    private val backupManager = BackupManager(application)

    val isDarkMode = settingsManager.isDarkMode.asLiveData()

    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> get() = _notificationsEnabled

    private val _backupState = MutableLiveData<BackupState>()
    val backupState: LiveData<BackupState> get() = _backupState

    private val _characterList = MutableLiveData<List<Character>?>()

    init {
        loadNotificationsSetting()
        checkBackupStatus()
    }

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsManager.setTheme(isDarkMode)
            applyTheme(isDarkMode)
        }
    }

    private fun applyTheme(isDarkMode: Boolean) {
        val mode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun loadNotificationsSetting() {
        _notificationsEnabled.value = settingsManager.getNotificationsEnabled()
    }

    fun saveNotificationsSetting(isEnabled: Boolean) {
        settingsManager.setNotificationsEnabled(isEnabled)
        _notificationsEnabled.value = isEnabled
    }

    fun checkBackupStatus(fileName: String = "601-650.txt") {
        val externalFile = backupManager.getExternalFile(fileName)
        val internalFile = backupManager.getInternalFile()
        _backupState.value = BackupState(
            externalFileExists = externalFile.exists(),
            externalFilePath = if (externalFile.exists()) externalFile.absolutePath else "Не найден",
            internalFileExists = internalFile.exists()
        )
    }

    private fun fetchCharactersForBackup(onComplete: (List<Character>) -> Unit) {
        viewModelScope.launch {
            try {
                val characterIds = (601..650).joinToString(",")
                val response = RetrofitInstance.api.getCharacters(characterIds)
                _characterList.postValue(response)
                onComplete(response)
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun createBackup(fileName: String) {
        fetchCharactersForBackup { characters ->
            viewModelScope.launch {
                val currentSettings = AppSettings(
                    isDarkMode = settingsManager.isDarkMode.first(),
                    notificationsEnabled = settingsManager.getNotificationsEnabled()
                )
                val backupData = BackupData(settings = currentSettings, characters = characters)
                backupManager.createExternalBackup(fileName, backupData)
                checkBackupStatus(fileName)
            }
        }
    }

    fun deleteBackup(fileName: String) {
        val externalFile = backupManager.getExternalFile(fileName)
        backupManager.createInternalBackupFromExternal(externalFile)
        backupManager.deleteExternalBackup(externalFile)
        checkBackupStatus(fileName)
    }

    fun restoreBackup(fileName: String) {
        val backupData = backupManager.readBackupFromInternal()
        if (backupData != null) {
            viewModelScope.launch {
                settingsManager.setTheme(backupData.settings.isDarkMode)
                settingsManager.setNotificationsEnabled(backupData.settings.notificationsEnabled)
                loadNotificationsSetting()
                applyTheme(backupData.settings.isDarkMode)
            }

            val externalFile = backupManager.getExternalFile(fileName)
            backupManager.restoreBackupFromInternal(externalFile)
            checkBackupStatus(fileName)
        }
    }
}