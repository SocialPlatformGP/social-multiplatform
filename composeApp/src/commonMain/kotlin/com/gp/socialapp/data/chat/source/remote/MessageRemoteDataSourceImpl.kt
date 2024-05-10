package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
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

    override fun fetchChatMessages(request: MessageRequest.FetchMessages): Flow<Result<List<Message>,ChatError.Temp>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchChatMessages")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val messages = response.body<List<Message>>()
                    emit(success(messages))
                } else {
                    emit(Result.Error(ChatError.Temp.SERVER_ERROR))
                }
            } catch (e: Exception) {
                    emit(Result.Error(ChatError.Temp.SERVER_ERROR))
            }
        }

    override suspend fun sendMessage(request: MessageRequest.SendMessage) =
        socketService.sendMessage(request)

    override suspend fun updateMessage(request: MessageRequest.UpdateMessage): Result<Unit,ChatError.Temp> {
        return try {
            val response = httpClient.post {
                endPoint("updateMessage")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
            }
        } catch (e: Exception) {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
        }
    }

    override suspend fun observeMessages(): Flow<Result<NewDataResponse,ChatError.Temp>> {
        println("im in message remote data source")
        return socketService.observeNewDataMessage()
    }

    override suspend fun deleteMessage(request: MessageRequest.DeleteMessage): Result<Unit,ChatError.Temp> {
        return try {
            val response = httpClient.post {
                endPoint("deleteMessage")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
            }
        } catch (e: Exception) {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
        }
    }

    override suspend fun reportMessage(request: MessageRequest.ReportMessage): Result<Unit,ChatError.Temp> {
        return try {
            val response = httpClient.post {
                endPoint("reportMessage")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
            }
        } catch (e: Exception) {
                    Result.Error(ChatError.Temp.SERVER_ERROR)
        }
    }

    override suspend fun closeSocket() {
        socketService.closeSocket()
    }

}