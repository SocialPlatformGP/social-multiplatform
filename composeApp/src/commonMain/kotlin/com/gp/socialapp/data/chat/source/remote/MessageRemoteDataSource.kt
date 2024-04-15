package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {

    fun openRoomSocket(
        userId: String,
        roomId: String,
    ): Flow<Result<Unit>>

    suspend fun closeRoomSocket()

    suspend fun sendMessage(
        message: Message
    )

    fun getMessages(): Flow<Result<List<Message>>>
}