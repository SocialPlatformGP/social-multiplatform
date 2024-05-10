package com.gp.socialapp.data.chat.source.remote


import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.model.RemoteMessage
import com.gp.socialapp.data.chat.source.remote.model.RemoteRecentRoom
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
        roomId: Long, scope: CoroutineScope
    ): Flow<Result<List<Message>>> = callbackFlow {
        trySend(Result.Loading)
        try {
            supabase.from(MESSAGES).selectAsFlow(
                RemoteMessage::id, filter = FilterOperation("roomId", FilterOperator.EQ, roomId)
            ).collect {
                println("received data in remote source :$it")
                trySend(Result.SuccessWithData(it.map { it.toMessage() }
                    .sortedByDescending { it.createdAt }))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.Error("Error fetching messages: ${e.message}"))
        }
        awaitClose()
    }

    override suspend fun sendMessage(
        messageContent: String,
        roomId: Long,
        senderId: String,
        senderName: String,
        senderPfpUrl: String,
        attachment: MessageAttachment,
    ): Result<Nothing> {
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
                    set("lastMessage", messageContent)
                    set("lastMessageTime", createdRemoteMessage.createdAt)
                    set("lastMessageId", createdRemoteMessage.id)
                }) {
                    filter {
                        eq("roomId", roomId)
                    }
                }
                return Result.Success
            } else {
                uploadAttachment(roomId, senderId, attachment).let { result ->
                    if (result is Result.SuccessWithData) {
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
                        supabase.from(RECENTROOMS).update({
                            set("lastMessage", messageContent)
                            set("lastMessageTime", createdRemoteMessage.createdAt)
                            set("lastMessageId", createdRemoteMessage.id)
                        }) {
                            filter {
                                eq("roomId", roomId)
                            }
                        }
                        return Result.Success
                    } else {
                        return Result.Error("Error uploading attachment: ${(result as Result.Error).message}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error("Error sending message: ${e.message}")
        }
    }

    override suspend fun updateMessage(
        messageId: Long, roomId: Long, content: String
    ): Result<Nothing> {
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
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error updating message: ${e.message}")
        }
    }

    override suspend fun deleteMessage(messageId: Long, roomId: Long): Result<Nothing> {
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

            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error deleting message: ${e.message}")
        }
    }

    private suspend fun uploadAttachment(
        roomId: Long, senderId: String, attachment: MessageAttachment
    ): Result<MessageAttachment> {
        return try {
            val epochSeconds = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
            val path = "${roomId}/$senderId/${epochSeconds}-${attachment.name}"
            val bucket = supabase.storage.from("chat_attachments")
            val key = bucket.upload(path, attachment.byteArray, upsert = true)
            print("attachment uploaded: $key")
            val url = supabase.storage.from("chat_attachments").publicUrl(path)
            println("attachment url: $url")
            Result.SuccessWithData(
                attachment.copy(
                    url = url, byteArray = byteArrayOf(), path = path
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("An error occurred uploading attachment: ${e.message}")
        }

    }

}