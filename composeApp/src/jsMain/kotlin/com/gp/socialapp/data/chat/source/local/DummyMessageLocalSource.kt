package com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DummyMessageLocalSource: MessageLocalDataSource {
    override fun getMessagesFlow(roomId: String): Flow<Result<List<Message>>> = flow {}

    override suspend fun insertMessages(vararg messages: Message): Result<Nothing> = Result.Idle

    override suspend fun deleteMessage(messageId: String): Result<Nothing> = Result.Idle

    override suspend fun updateMessage(message: Message): Result<Nothing> = Result.Idle
}