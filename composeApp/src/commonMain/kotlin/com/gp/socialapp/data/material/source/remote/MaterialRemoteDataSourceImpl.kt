package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.requests.MaterialRequest
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val BASE_URL = "http://192.168.1.4:8080/"

class MaterialRemoteDataSourceImpl(
    val fileManager: FileManager,
    val client: HttpClient
) : MaterialRemoteDataSource {
    private fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getMaterialAtPath(path: String): Flow<Result<MaterialResponse.GetMaterialResponses>> {
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
                val data = response.body<MaterialResponse.GetMaterialResponses>()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(data))
                } else {
                    emit(Result.Error("An unknown error occurred"))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

    override suspend fun createFolder(
        name: String,
        path: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        val request = MaterialRequest.CreateFolderRequest(name = name, path = path)
        return flow {
            emit(Result.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("uploadFolder")
                    setBody(
                        request
                    )
                }
                val data = response.body<MaterialResponse.GetMaterialResponses>()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(data))
                } else {
                    emit(Result.Error("An unknown error occurred"))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

    override suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        val request = MaterialRequest.CreateFileRequest(
            name = name,
            type = type,
            path = path,
            content = content
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
                val data = response.body<MaterialResponse.GetMaterialResponses>()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(data))
                } else {
                    emit(Result.Error("An unknown error occurred"))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        val request = MaterialRequest.DeleteFileRequest(fileId, path)
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post(fileId) {
                    endPoint("deleteFile")
                    setBody(request)
                }
                val data = response.body<MaterialResponse.GetMaterialResponses>()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(data))
                } else {
                    emit(Result.Error("Failed to delete file"))
                }
            } catch (e: Exception) {
                Napier.e("Error deleting file: ${e.message}")
                emit(Result.Error("Error"))
            }
        }
    }

    override suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.delete(folderId) {
                    endPoint("deleteFile")
                }
                val data = response.body<MaterialResponse.GetMaterialResponses>()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(data))
                } else {
                    emit(Result.Error("Failed to delete folder"))
                }
            } catch (e: Exception) {
                Napier.e("Error deleting folder: ${e.message}")
                emit(Result.Error("Error"))
            }
        }
    }

    override suspend fun downloadFile(url: String): Result<MaterialResponse.DownloadFileResponse> {
        val response = this.client.post {
            endPoint("download")
            setBody(
                MaterialRequest.DownloadFileRequest(url)
            )
        }
        println(response)
        if (response.status == HttpStatusCode.OK) {
            println(response)
            val data = response.body<MaterialResponse.DownloadFileResponse>()
            return Result.SuccessWithData(data)
            println("Downloaded file")
        } else {
            println("Failed to download file")
            return Result.Error("Failed to download file")
        }
    }
}



