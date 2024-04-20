package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File

actual class FileManagerImpl() : FileManager {
    val home = System.getProperty("user.home")
    override suspend fun saveFile(data: ByteArray): String {
        val folder = File(home + "/Downloads/eduLink")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val file =
            File(folder.path + "/m8.xlsx")//todo get the file name from the server and save it
        file.writeBytes(data)
        println("File saved at ${file.path}")
        withContext(DispatcherIO) {
            Desktop.getDesktop().open(file)
        }
        return file.path
    }

}