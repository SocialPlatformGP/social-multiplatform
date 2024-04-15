package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    fun fetchChatMessages(request: MessageRequest.FetchMessages): Flow<Result<List<Message>>>
    suspend fun sendMessage(request: MessageRequest.SendMessage): Result<Nothing>
    suspend fun updateMessage(request: MessageRequest.UpdateMessage): Result<Nothing>
    suspend fun deleteMessage(request: MessageRequest.DeleteMessage): Result<Nothing>
}