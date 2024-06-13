package com.gp.socialapp.presentation.app.com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.material.utils.FileManager

object DummyFileManager : FileManager {
    override suspend fun saveFile(file: ByteArray, fileName: String, mimeType: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun openFile(filePath: String, mimeType: String) {
        TODO("Not yet implemented")
    }


    override suspend fun openLink(url: String) {
        TODO("Not yet implemented")
    }
}