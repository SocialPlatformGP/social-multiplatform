package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val socketService: SocketService
) : MessageRemoteDataSource {
    override suspend fun connectToSocket(userId: String, roomId: String) =
        socketService.connectToSocket(userId)

    override fun fetchChatMessages(request: MessageRequest.FetchMessages): Flow<Result<List<Message>>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchChatMessages")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val messages = response.body<List<Message>>()
//                    println("Messages: $messages")
                    emit(Result.SuccessWithData(messages))
                } else {
                    emit(Result.Error("An error occurred: ${response.status.description}"))
                }
            } catch (e: Exception) {
                emit(Result.Error("An error occurred: ${e.message}"))
            }
        }

    override suspend fun sendMessage(request: MessageRequest.SendMessage) =
        socketService.sendMessage(request)

    override suspend fun updateMessage(request: MessageRequest.UpdateMessage): Result<Nothing> {
        return try {
            val response = httpClient.post {
                endPoint("updateMessage")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun observeMessages(): Flow<Result<NewDataResponse>> {
        println("im in message remote data source")
        return socketService.observeNewDataMessage()
    }

    override suspend fun deleteMessage(request: MessageRequest.DeleteMessage): Result<Nothing> {
        return try {
            val response = httpClient.post {
                endPoint("deleteMessage")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun closeSocket() {
        socketService.closeSocket()
    }

}