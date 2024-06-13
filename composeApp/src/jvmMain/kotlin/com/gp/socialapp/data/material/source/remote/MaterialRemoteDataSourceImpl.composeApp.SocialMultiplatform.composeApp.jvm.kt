package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.util.AppConstants.BASE_URL
import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File
import java.net.URI

class FileManagerImpl() : FileManager {
    val home = System.getProperty("user.home")
    override suspend fun saveFile(data: ByteArray, fileName: String, mimeType: String): String {
        val folder = File(home + "/Downloads/eduLink")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val file =
            File(folder.path + "/" + fileName)
        file.writeBytes(data)
        println("File saved at ${file.path}")
        return file.path
    }

    override suspend fun openFile(filePath: String, mimeType: String) {
        val file = File(filePath)
        withContext(DispatcherIO) {
            Desktop.getDesktop().open(file)
        }
    }


    override suspend fun openLink(url: String) {
        val link = "$BASE_URL$url".replace(" ", "%20")
        withContext(Dispatchers.IO) {
            Desktop.getDesktop().browse(URI(link))
        }
    }


}