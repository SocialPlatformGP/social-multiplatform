package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface SocketService {
    fun connectToSocket(userId: String): Flow<Result<Nothing>>
    fun getMessages(userId: String): Flow<SocketMessage>
}