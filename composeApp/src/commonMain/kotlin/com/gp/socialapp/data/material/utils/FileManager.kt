package com.gp.socialapp.data.material.utils

interface FileManager {
    suspend fun saveFile(file: ByteArray, fileName: String, mimeType: String): String
    suspend fun openFile(filePath: String, mimeType: String)
}