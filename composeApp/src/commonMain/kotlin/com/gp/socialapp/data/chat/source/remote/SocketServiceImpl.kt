package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.Serializable

class SocketServiceImpl(
    private val client: HttpClient
) : SocketService {
    var socket: WebSocketSession? = null
    override suspend fun connectToSocket(userId: String): Result<Nothing> {
        return try {
            println("userId: $userId")
            socket = client.webSocketSession {
//                url {
//                    protocol = URLProtocol.WS
//                    host = "192.168.1.4"
//                    port = 8080
//                    encodedPath = "/open-room-socket"
//                    parameters.append("user-id", userId)
//                }
                url("ws://192.168.1.4:8080//open-room-socket?user-id=$userId")
            }
            if (socket?.isActive == true) {
                println("socket connected")
                Result.Success
            } else {
                println("socket not connected")
                Result.Error("An error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    override fun getMessages(
        userId: String
    ) = flow<SocketMessage> {

    }

}

@Serializable
data class SocketMessage(
    val message: String
)