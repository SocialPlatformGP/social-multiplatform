package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.AppConstants.SOCKET_URL
import com.gp.socialapp.util.ChatError
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
    var sharedFlow = MutableSharedFlow<Result<NewDataResponse,ChatError.Temp>>()
    override suspend fun connectToSocket(userId: String): Result<Unit, ChatError.Temp> {
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
                Result.Success(Unit)
            } else {
                println("socket not connected")
            Result.Error(ChatError.Temp.SERVER_ERROR)
            }
        } catch (e: Exception) {
            Result.Error(ChatError.Temp.SERVER_ERROR)
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
                Result.Success(
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

    override suspend fun observeNewData(): Flow<Result<NewDataResponse, ChatError.Temp>> {
        return sharedFlow
    }

    override suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Unit, ChatError.Temp> {
        println("message: $message")
        return try {
            socket?.send(Frame.Text(Json.encodeToString(message)))
            println("message sent via socket $socket")
            Result.Success(Unit)
        } catch (e: Exception) {
            println("error: ${e.message}")
            e.printStackTrace()
            Result.Error(ChatError.Temp.SERVER_ERROR)
        }
    }

    override suspend fun closeSocket(): Result<Unit, ChatError.Temp> {
        socket?.close()
        return Result.Success(Unit)
    }

    override suspend fun observeNewDataMessage(): Flow<Result<NewDataResponse, ChatError.Temp>> {
        return sharedFlow
    }
}
