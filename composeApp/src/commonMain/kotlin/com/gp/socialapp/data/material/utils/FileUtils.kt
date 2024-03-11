package com.gp.material.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.gp.socialapp.database.model.MimeType
import java.util.Locale

object FileUtils {
        @SuppressLint("Range")
     fun getFileName(uri: Uri,context: Context): String {
        var fileName = ""
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                fileName = displayName ?: ""
            }
        }
        return fileName
    }

     fun getMimeTypeFromUri(uri: Uri,context: Context): String? {
         Log.d("zarea15", "getMimeTypeFromUri:$uri ")
        val contentResolver: ContentResolver = context.contentResolver
        var mimeType: String? = null

        // Try to query the ContentResolver to get the MIME type
        mimeType = contentResolver.getType(uri)
         Log.d("zarea151", "getMimeTypeFromUri: $mimeType")

        if (mimeType == null) {
            // If ContentResolver couldn't determine the MIME type, try getting it from the file extension
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            Log.d("zarea151", "getMimeTypeFromUri: $fileExtension")

            if (!fileExtension.isNullOrEmpty()) {
                mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(fileExtension.toLowerCase(Locale.US))

            }
        }

        return mimeType
    }
    fun getEnumMimeTypeFromUri(uri: Uri, context: Context): MimeType {
        val contentResolver: ContentResolver = context.contentResolver
        var mimeType: MimeType? = null

        // Try to query the ContentResolver to get the MIME type
        val mimeTypeString = contentResolver.getType(uri)
        if (mimeTypeString != null) {
            // If ContentResolver determined the MIME type, find the corresponding enum
            mimeType = MimeType.values().find { it.value == mimeTypeString }
            Log.d("seerde", "MimeType: $mimeType , MimeTypeString: $mimeTypeString")
        }

        if (mimeType == null) {
            // If ContentResolver couldn't determine the MIME type, try getting it from the file extension
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            if (!fileExtension.isNullOrEmpty()) {
                // Use the enum class to get the MIME type from the file extension
                Log.d("seerde", "1- MimeType: $mimeType , FileExtension: $fileExtension")
                mimeType = MimeType.values().find { it.name.equals(fileExtension, ignoreCase = true) }
            }
            Log.d("seerde", "2- MimeType: $mimeType , FileExtension: $fileExtension")
        }

        return mimeType ?: MimeType.ALL_FILES
    }
    fun getFileSizeString(sizeBytes: Long): String {
        val kiloBytes = sizeBytes / 1024.0
        val megaBytes = kiloBytes / 1024.0
        val gigaBytes = megaBytes / 1024.0

        return when {
            gigaBytes >= 1.0 -> String.format("%.2f GB", gigaBytes)
            megaBytes >= 1.0 -> String.format("%.2f MB", megaBytes)
            kiloBytes >= 1.0 -> String.format("%.2f KB", kiloBytes)
            else -> String.format("%d B", sizeBytes)
        }
    }

}
