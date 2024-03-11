package com.gp.material.repository


import com.eygraber.uri.Uri
import com.gp.material.model.FileType
import com.gp.material.model.MaterialItem
import com.gp.material.source.remote.MaterialRemoteDataSource
import kotlinx.coroutines.flow.Flow
import com.gp.socialapp.util.Result

class MaterialRepositoryImpl (private val remoteDataSource: MaterialRemoteDataSource): MaterialRepository{
    override fun getListOfFiles(path: String): Flow<Result<List<MaterialItem>>> {
        return remoteDataSource.getListOfFiles(path)
    }

    override fun uploadFile(fileLocation: String, file: Uri): Flow<Result<Nothing>> {
        return remoteDataSource.uploadFile(fileLocation, file)
    }

    override fun uploadFolder(fileLocation: String, name: String): Flow<Result<Nothing>> {
        return remoteDataSource.uploadFolder(fileLocation, name)
    }

    override fun deleteFile(fileLocation: String) : Flow<Result<Nothing>> {
        return remoteDataSource.deleteFile(fileLocation)
    }

    override fun deleteFolder(folderPath: String) : Flow<Result<Nothing>>{
        return remoteDataSource.deleteFolder(folderPath)
    }

    override fun getFileTypeFromName(fileName: String): FileType {
        return remoteDataSource.getFileTypeFromName(fileName)
    }
    override fun uploadMaterialItemToDatabase(materialItem: MaterialItem) {
        return remoteDataSource.uploadMaterialItemToDatabase(materialItem)
    }
}