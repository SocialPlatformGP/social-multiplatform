package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.close
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class MessageRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : MessageRemoteDataSource {
    private var socket: WebSockets? = null
    override fun openRoomSocket(userId: String, roomId: String): Flow<Result<Unit>> {
        println("Opening socket")
        println("User ID: $userId Room ID: $roomId")
        return flow {
            try {
                socket = httpClient.webSocket(
                    method = HttpMethod.Get,
                    host = "127.0.0.1",
                    port = 8080,
                    path = "/customer/1"
                ) {
                    val customer = receiveDeserialized<Customer>()
                    println("A customer with id ${customer.id} is received by the client.")
                }
                if (socket?.isActive == true) {
                    println("Socket is active")
                    emit(Result.Success)
                } else {
                    println("Socket is not active")
                    emit(Result.Error("An error occurred"))
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                emit(Result.Error("An error occurred: ${e.message}"))

            }
        }
    }

    override suspend fun closeRoomSocket() {
        println("Closing socket")
        socket?.close()
    }

    override suspend fun sendMessage(message: Message) {
        println("Sending message")
        println("Message: $message")
        try {
            socket.sen
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    override fun getMessages(): Flow<Result<List<Message>>> {
        TODO("Not yet implemented")
    }
}