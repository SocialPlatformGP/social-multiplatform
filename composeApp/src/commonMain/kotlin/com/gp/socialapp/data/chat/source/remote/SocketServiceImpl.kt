package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable

class SocketServiceImpl(
    private val client: HttpClient
) : SocketService {
    var socket: DefaultClientWebSocketSession? = null
    override fun connectToSocket(userId: String): Flow<Result<Nothing>> = flow {
        client.webSocket(
            method = HttpMethod.Get,
            host = "192.168.1.4",
            port = 8080,
            path = "open-room-socket"
        ) {
            println("Sending message")
            sendSerialized(SocketMessage(userId))
            socket = this
            emit(com.gp.socialapp.util.Result.Success)
        }


    }

    override fun getMessages(
        userId: String
    ) = flow<SocketMessage> {
        println("getMessages")
        println("socket: $socket")
        socket?.incoming?.consumeEach {
            println("incoming")
            emit(socket!!.receiveDeserialized<SocketMessage>())
        }

    }

}

@Serializable
data class SocketMessage(
    val message: String
)