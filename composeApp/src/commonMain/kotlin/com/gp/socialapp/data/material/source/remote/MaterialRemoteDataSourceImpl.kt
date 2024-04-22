package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.requests.MaterialRequest
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class MaterialRemoteDataSourceImpl(
    val client: HttpClient
) : MaterialRemoteDataSource {
    override suspend fun getMaterialAtPath(path: String): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>> {
        val request = MaterialRequest.GetMaterialRequest(path)
        return flow {
            emit(Results.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("get_files")
                    setBody(
                        request
                    )
                }
                handleServerResponse(response)
            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }


    override suspend fun createFolder(
        name: String,
        path: String,
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>> {
        val request = MaterialRequest.CreateFolderRequest(name = name, path = path)
        return flow {
            emit(Results.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("uploadFolder")
                    setBody(
                        request
                    )
                }
                handleServerResponse(response)
            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }

    override suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>> {
        val request = MaterialRequest.CreateFileRequest(
            name = name,
            type = type,
            path = path,
            content = content
        )
        return flow {
            emit(Results.Loading)
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post {
                    endPoint("uploadFile")
                    setBody(
                        request
                    )
                }
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>> {
        val request = MaterialRequest.DeleteFileRequest(fileId, path)
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post(fileId) {
                    endPoint("deleteFile")
                    setBody(request)
                }
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFolder(folderId: String): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>> {
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.delete(folderId) {
                    endPoint("deleteFile")
                }
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }

    override suspend fun downloadFile(url: String): Results<MaterialResponse.DownloadFileResponse, DataError.Network> {
        return try {
            val response = client.post {
                endPoint("download")
                setBody(
                    MaterialRequest.DownloadFileRequest(url)
                )
            }
            val data = response.body<MaterialResponse.DownloadFileResponse>()
            if (response.status == HttpStatusCode.OK) {
                Results.Success(data)
            } else {
                when (response.status) {
                    HttpStatusCode.NotFound -> Results.Failure(DataError.Network.NOT_FOUND)
                    HttpStatusCode.BadRequest -> Results.Failure(DataError.Network.BAD_REQUEST)
                    else -> Results.Failure(DataError.Network.FILE_NOT_FOUND)
                }
            }
        } catch (e: Exception) {
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }
    }
}

private suspend fun FlowCollector<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>.handleServerResponse(
    response: HttpResponse
) {
    val data = response.body<MaterialResponse.GetMaterialResponses>()
    if (response.status == HttpStatusCode.OK) {
        emit(Results.Success(data))
    } else {
        when (response.status) {
            HttpStatusCode.NotFound -> emit(Results.Failure(DataError.Network.NOT_FOUND))
            HttpStatusCode.BadRequest -> emit(Results.Failure(DataError.Network.BAD_REQUEST))
            HttpStatusCode.PayloadTooLarge -> emit(Results.Failure(DataError.Network.PAYLOAD_TOO_LARGE))
            else -> emit(Results.Failure(DataError.Network.CANT_GET_FILES_AT_PATH))
        }
    }
}