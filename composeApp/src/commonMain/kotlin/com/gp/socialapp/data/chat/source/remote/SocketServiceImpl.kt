package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.AppConstants.SOCKET_URL
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SocketServiceImpl(
    private val client: HttpClient
) : SocketService {
    var socket: DefaultClientWebSocketSession? = null
    var sharedFlow = MutableSharedFlow<Result<NewDataResponse>>()
    override suspend fun connectToSocket(userId: String): Result<Nothing> {
        return try {
            println("userId: $userId")
            socket = client.webSocketSession {
                url {
                    takeFrom(SOCKET_URL)
                    path("/chatSocket")
                    parameter("userid", userId)
                }
            }
            if (socket?.isActive == true) {
                println("Roomsocket connected")
                connectToRecentSocket()
                Result.Success
            } else {
                println("socket not connected")
                Result.Error("An error occurred")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun connectToRecentSocket() {
        try {
            socket?.incoming?.receiveAsFlow()?.filter {
                it is Frame.Text
            }?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                val response = Json.decodeFromString<NewDataResponse>(json)
                println("messageResponse: ${response.messages?.content}")
                Result.SuccessWithData(
                    response
                )
            }?.collect {
                sharedFlow.emit(it)
                it.data.messages?.let {
                    println("messageResponse2: ${it.content}")
                }
                println(sharedFlow.subscriptionCount.value)
                sharedFlow.shareIn(
                    CoroutineScope(DispatcherIO),
                    replay = 2,
                    started = SharingStarted.WhileSubscribed()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun observeNewData(): Flow<Result<NewDataResponse>> {
        return sharedFlow
    }

    override suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Nothing> {
        println("message: $message")
        return try {
            socket?.send(Frame.Text(Json.encodeToString(message)))
            println("message sent via socket $socket")
            Result.Success
        } catch (e: Exception) {
            println("error: ${e.message}")
            e.printStackTrace()
            Result.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun closeSocket(): Result<Nothing> {
        socket?.close()
        return Result.Success
    }

    override suspend fun observeNewDataMessage(): Flow<Result<NewDataResponse>> {
        return sharedFlow
    }
}
