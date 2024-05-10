package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.requests.MaterialRequest
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MaterialRemoteDataSourceImpl(
    val client: HttpClient
) : MaterialRemoteDataSource {
    override suspend fun getMaterialAtPath(path: String): Flow<Result<MaterialResponse.GetMaterialResponses,MaterialError.GetMaterial>> {
        val request = MaterialRequest.GetMaterialRequest(path)
        return flow {
            emit(Result.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("get_files")
                    setBody(
                        request
                    )
                }
                if(response.status == HttpStatusCode.OK){
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                }else{
                    emit(Result.Error(MaterialError.GetMaterial.SERVER_ERROR))
                }
            } catch (e: Exception) {
                emit(Result.Error(MaterialError.GetMaterial.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }


    override suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.CreateFolder>> {
        val request =
            MaterialRequest.CreateFolderRequest(name = name, path = path, communityId = communityId)
        return flow {
            emit(Result.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("uploadFolder")
                    setBody(
                        request
                    )
                }
                if(response.status == HttpStatusCode.OK){
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                }else{
                    emit(Result.Error(MaterialError.CreateFolder.SERVER_ERROR))
                }            } catch (e: Exception) {
                emit(Result.Error(MaterialError.CreateFolder.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray,
        communityId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.CreateFile>> {
        val request = MaterialRequest.CreateFileRequest(
            name = name,
            type = type,
            path = path,
            content = content,
            communityId = communityId
        )
        return flow {
            emit(Result.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("uploadFile")
                    setBody(
                        request
                    )
                }
                if(response.status == HttpStatusCode.OK){
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                }else{
                    emit(Result.Error(MaterialError.CreateFile.SERVER_ERROR))
                }
            } catch (e: Exception) {
                emit(Result.Error(MaterialError.CreateFile.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.DeleteFile>> {
        val request = MaterialRequest.DeleteFileRequest(fileId, path)
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post(fileId) {
                    endPoint("deleteFile")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(MaterialError.DeleteFile.SERVER_ERROR))
                }

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.DeleteFile.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.DeleteFolder>> {
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.delete(folderId) {
                    endPoint("deleteFile")
                }
                if (response.status == HttpStatusCode.OK) {
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(MaterialError.DeleteFolder.SERVER_ERROR))
                }

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.DeleteFolder.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun downloadFile(url: String): Result<MaterialResponse.DownloadFileResponse, MaterialError.DownloadFile> {
        return try {
            val response = client.post {
                endPoint("download")
                setBody(
                    MaterialRequest.DownloadFileRequest(url)
                )
            }
            val data = response.body<MaterialResponse.DownloadFileResponse>()
            if (response.status == HttpStatusCode.OK) {
                Result.Success(data)
            } else {
                error(MaterialError.DownloadFile.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(MaterialError.DownloadFile.SERVER_ERROR)
        }
    }

    override fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.RenameFolder>> {
        val request = MaterialRequest.RenameFolderRequest(
            folderId = folderId,
            newName = newName
        )
        return flow {
            emit(Result.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("renameFolder")
                    setBody(
                        request
                    )
                }
                if(response.status == HttpStatusCode.OK){
                    val data = response.body<MaterialResponse.GetMaterialResponses>()
                    emit(Result.Success(data))
                }else{
                    emit(Result.Error(MaterialError.RenameFolder.SERVER_ERROR))

                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(MaterialError.RenameFolder.SERVER_ERROR))
            }
        }
    }
}