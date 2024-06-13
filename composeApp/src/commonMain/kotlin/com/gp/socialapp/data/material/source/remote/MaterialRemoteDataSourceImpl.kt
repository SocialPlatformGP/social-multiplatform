package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.requests.MaterialRequest
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
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
    val client: HttpClient,
    private val supabase: SupabaseClient
) : MaterialRemoteDataSource {
    override suspend fun downloadChatAttachment(path: String): Result<MaterialResponse.DownloadChatAttachment, ChatError> {
        return try{
            val bucket = supabase.storage.from("chat_attachments")
            println("Path: $path")
            val bytes = bucket.downloadPublic(path)
            println("Bytes size: ${bytes.size}")
            Result.Success(MaterialResponse.DownloadChatAttachment(bytes))
        } catch(e: Exception) {
            Result.Error(ChatError.SERVER_ERROR)
        }

    }

    override suspend fun getMaterialAtPath(path: String): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
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
                handleServerResponse(response)
            } catch (e: Exception) {
                println("Exception: $e")
                emit(Result.Error(MaterialError.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }


    override suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
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
                handleServerResponse(response)
            } catch (e: Exception) {
                emit(Result.Error(MaterialError.SERVER_ERROR))
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
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
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
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        val request = MaterialRequest.DeleteFileRequest(fileId, path)
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.post(fileId) {
                    endPoint("deleteFile")
                    setBody(request)
                }
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return flow {
            try {
                val response = this@MaterialRemoteDataSourceImpl.client.delete(folderId) {
                    endPoint("deleteFile")
                }
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }

    override suspend fun downloadFile(url: String): Result<MaterialResponse.DownloadFileResponse, MaterialError> {
        return try {
            println("Downloading file from remote source $url")
            val response = client.post {
                endPoint("download")
                setBody(
                    MaterialRequest.DownloadFileRequest(url)
                )
            }
            val data = response.body<MaterialResponse.DownloadFileResponse>()
            if (response.status == HttpStatusCode.OK) {
                println("Success")
                Result.Success(data)
            } else {
                println("Error")
                Result.Error(MaterialError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(MaterialError.SERVER_ERROR)
        }
    }

    override fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
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
                handleServerResponse(response)

            } catch (e: Exception) {
                emit(Result.Error(MaterialError.SERVER_ERROR))
                e.printStackTrace()
            }
        }
    }
}

private suspend fun FlowCollector<Result<MaterialResponse.GetMaterialResponses, MaterialError>>.handleServerResponse(
    response: HttpResponse
) {

    if (response.status == HttpStatusCode.OK) {
        val data = response.body<MaterialResponse.GetMaterialResponses>()
        println("Data: $data")
        emit(Result.Success(data))
    } else {
        val error = response.body<MaterialError>()
        println("Error: $error")
        emit(Result.Error(MaterialError.SERVER_ERROR))
    }
}