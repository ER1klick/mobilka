package com.example.lab_1

import android.content.Context
import android.os.Environment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class BackupManager(private val context: Context) {

    private val internalFile = File(context.filesDir, "backup.json")

    fun createExternalBackup(fileName: String, backupData: BackupData): File {
        val content = Json.encodeToString(backupData)
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, fileName.replace(".txt", ".json"))
        file.writeText(content)
        return file
    }

    fun getExternalFile(fileName: String): File {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        return File(directory, fileName.replace(".txt", ".json"))
    }

    fun createInternalBackupFromExternal(externalFile: File) {
        if (externalFile.exists()) {
            internalFile.writeText(externalFile.readText())
        }
    }

    fun deleteExternalBackup(externalFile: File) {
        if (externalFile.exists()) {
            externalFile.delete()
        }
    }

    fun getInternalFile(): File {
        return internalFile
    }

    fun readBackupFromInternal(): BackupData? {
        return if (internalFile.exists()) {
            try {
                Json.decodeFromString<BackupData>(internalFile.readText())
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun restoreBackupFromInternal(externalFile: File) {
        if (internalFile.exists()) {
            externalFile.parentFile?.mkdirs()
            externalFile.writeText(internalFile.readText())
        }
    }
}