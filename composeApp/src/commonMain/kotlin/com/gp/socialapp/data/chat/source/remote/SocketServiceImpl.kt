package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.util.AppConstants.SOCKET_URL
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SocketServiceImpl(
    private val client: HttpClient
) : SocketService {
    var socket: DefaultClientWebSocketSession? = null
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
                println("socket connected")
                Result.Success
            } else {
                println("socket not connected")
                Result.Error("An error occurred")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }


    override fun observeMessages(): Flow<Result<Message>> {
        return try {
            socket?.incoming?.receiveAsFlow()?.filter {
                it is Frame.Text
            }?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                val messageResponse = Json.decodeFromString<Message>(json)
                println("messageDto: $messageResponse")
                Result.SuccessWithData(messageResponse)
            } ?: flow {}
        } catch (e: Exception) {
            e.printStackTrace()
            flow {}
        }
    }


    override suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Nothing> {
        println("message: $message")
        return try {
            socket?.send(Frame.Text(Json.encodeToString(message)))
            println("message sent via socket $socket")
            Result.Success
        } catch (e: Exception) {
            println("error: ${e.message}")
            Result.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun closeSocket(): Result<Nothing> {
        socket?.close()
        return Result.Success
    }
}
