@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
package com.example.lab_1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val isDarkMode: Boolean,
    val notificationsEnabled: Boolean
)

@Serializable
data class BackupData(
    val settings: AppSettings,
    val characters: List<Character>
)