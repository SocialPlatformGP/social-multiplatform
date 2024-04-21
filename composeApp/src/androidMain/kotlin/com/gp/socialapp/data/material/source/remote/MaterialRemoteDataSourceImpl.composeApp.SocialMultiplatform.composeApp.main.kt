package com.gp.socialapp.data.material.source.remote

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.gp.socialapp.AndroidApp.Companion.INSTANCE
import com.gp.socialapp.data.material.utils.FileManager

class FileManagerImpl : FileManager {
    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun saveFile(data: ByteArray, fileName: String, mimeType: String): String {
        val contentValue = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                fileName
            )
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = INSTANCE.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValue)
        if (uri != null) {
            resolver.openOutputStream(uri).use {
                it?.write(data)
            }
        }
        println("uri: ${uri?.path}")
        return uri.toString()

    }

    override suspend fun openFile(filePath: String, mimeType: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(filePath.toUri(), mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        INSTANCE.startActivity(intent)


    }


}