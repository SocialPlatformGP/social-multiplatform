package com.gp.socialapp.data.chat.source.remote


import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.model.RemoteMessage
import com.gp.socialapp.data.chat.source.remote.model.RemoteRecentRoom
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.storage.storage
import korlibs.time.DateTimeTz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class MessageRemoteDataSourceImpl(
    private val supabase: SupabaseClient
) : MessageRemoteDataSource {
    private val MESSAGES = "messages"
    private val RECENTROOMS = "recent_rooms"

    @OptIn(SupabaseExperimental::class)
    override suspend fun fetchChatMessages(
        roomId: Long
    ): Flow<Result<List<Message>,ChatError>> = flow {
        emit(Result.Loading)
        try {
            supabase.from(MESSAGES).selectAsFlow(
                RemoteMessage::id, filter = FilterOperation("roomId", FilterOperator.EQ, roomId)
            ).collect {
                println("received data in remote source :$it")
                emit(Result.Success(it.map { it.toMessage() }
                    .sortedByDescending { it.createdAt }))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(ChatError.SERVER_ERROR))
        }
    }

    override suspend fun sendMessage(
        messageContent: String,
        roomId: Long,
        senderId: String,
        senderName: String,
        senderPfpUrl: String,
        attachment: MessageAttachment,
    ): Result<Unit,ChatError> {
        try {
            if (attachment.type.isBlank()) {
                val message = RemoteMessage(
                    content = messageContent,
                    roomId = roomId,
                    senderId = senderId,
                    senderName = senderName,
                    senderPfpUrl = senderPfpUrl,
                    hasAttachment = false,
                    createdAt = DateTimeTz.nowLocal().format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                )
                println("message to be sent: $message")
                val createdRemoteMessage = supabase.from(MESSAGES).insert(message) {
                    select()
                }.decodeSingle<RemoteMessage>()
                supabase.from(RECENTROOMS).update({
                    set("lastMessageSenderName", senderName)
                    set("lastMessage", messageContent)
                    set("lastMessageTime", createdRemoteMessage.createdAt)
                    set("lastMessageId", createdRemoteMessage.id)
                }) {
                    filter {
                        eq("roomId", roomId)
                    }
                }
                return Result.Success(Unit)
            } else {
                uploadAttachment(roomId, senderId, attachment).let { result ->
                    if (result is Result.Success) {
                        val newAttachment = result.data
                        val message = RemoteMessage(
                            content = messageContent,
                            roomId = roomId,
                            senderId = senderId,
                            senderName = senderName,
                            senderPfpUrl = senderPfpUrl,
                            hasAttachment = true,
                            attachment = newAttachment,
                            createdAt = DateTimeTz.nowLocal()
                                .format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                        )
                        val createdRemoteMessage = supabase.from(MESSAGES).insert(message) {
                            select()
                        }.decodeSingle<RemoteMessage>()
                        val lastMessageContent = if (createdRemoteMessage.content.isBlank()) {
                            "Sent an attachment"
                        } else {
                            messageContent
                        }
                        supabase.from(RECENTROOMS).update({
                            set("lastMessage", lastMessageContent)
                            set("lastMessageTime", createdRemoteMessage.createdAt)
                            set("lastMessageId", createdRemoteMessage.id)
                        }) {
                            filter {
                                eq("roomId", roomId)
                            }
                        }
                        return Result.Success(Unit)
                    } else {
                        return Result.Error(ChatError.SERVER_ERROR)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun updateMessage(
        messageId: Long, roomId: Long, content: String
    ): Result<Unit,ChatError> {
        return try {
            supabase.from(MESSAGES).update({
                set("content", content)
            }) {
                filter {
                    eq("id", messageId)
                }
            }
            val recentRoom = supabase.from(RECENTROOMS).select {
                filter {
                    eq("roomId", roomId)
                }
            }.decodeSingle<RemoteRecentRoom>()
            if (recentRoom.lastMessageId == messageId) {
                supabase.from(RECENTROOMS).update({
                    set("lastMessage", content)
                }) {
                    filter {
                        eq("roomId", roomId)
                    }
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun deleteMessage(messageId: Long, roomId: Long): Result<Unit,ChatError> {
        return try {
            supabase.from(MESSAGES).delete {
                filter {
                    eq("id", messageId)
                }
            }
            val recentRoom = supabase.from(RECENTROOMS).select {
                filter {
                    eq("roomId", roomId)
                }
            }.decodeSingle<RemoteRecentRoom>()
            if (recentRoom.lastMessageId == messageId) {
                supabase.from(RECENTROOMS).update({
                    set("lastMessage", "Deleted message")
                }) {
                    filter {
                        eq("roomId", roomId)
                    }
                }
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun reportMessage(
        messageId: Long,
        roomId: Long,
        reporterId: String
    ): Result<Unit, ChatError> {
        TODO("Not yet implemented")
    }

    private suspend fun uploadAttachment(
        roomId: Long, senderId: String, attachment: MessageAttachment
    ): Result<MessageAttachment,ChatError> {
        return try {
            val epochSeconds = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
            val path = "${roomId}/$senderId/${epochSeconds}-${attachment.name}"
            val bucket = supabase.storage.from("chat_attachments")
            val key = bucket.upload(path, attachment.byteArray, upsert = true)
            print("attachment uploaded: $key")
            val url = supabase.storage.from("chat_attachments").publicUrl(path)
            println("attachment url: $url")
            Result.Success(
                attachment.copy(
                    url = url, byteArray = byteArrayOf(), path = path
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ChatError.SERVER_ERROR)
        }

    }

}