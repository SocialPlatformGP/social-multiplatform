package com.gp.material.repository

import com.eygraber.uri.Uri
import com.gp.material.model.FileType
import com.gp.material.model.MaterialItem
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    fun getListOfFiles(path: String): Flow<Result<List<MaterialItem>>>
    fun uploadFile(fileLocation:String, file: Uri): Flow<Result<Nothing>>
    fun uploadFolder(fileLocation: String,name:String): Flow<Result<Nothing>>
    fun deleteFile(fileLocation: String): Flow<Result<Nothing>>
    fun deleteFolder(folderPath:String): Flow<Result<Nothing>>
    fun getFileTypeFromName(fileName: String): FileType
    fun uploadMaterialItemToDatabase(materialItem: MaterialItem)
}