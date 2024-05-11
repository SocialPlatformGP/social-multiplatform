package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.data.chat.model.UserRooms
import com.gp.socialapp.data.chat.source.remote.model.RemoteRecentRoom
import com.gp.socialapp.data.chat.source.remote.model.RemoteRoom
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import korlibs.time.DateTimeTz.Companion.nowLocal
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class RoomRemoteDataSourceImpl(
    private val supabase: SupabaseClient
) : RoomRemoteDataSource {
    private val ROOMS = "rooms"
    private val RECENTROOMS = "recent_rooms"
    private val USERROOMS = "user_rooms"
    override suspend fun createGroupRoom(
        groupName: String,
        groupAvatarByteArray: ByteArray,
        groupAvatarExtension: String,
        userIds: List<String>,
        creatorId: String
    ): Result<Room> {
        return try {
            if (groupAvatarByteArray.isNotEmpty()) {
                uploadGroupAvatar(
                    groupAvatarByteArray, groupName, groupAvatarExtension
                ).let { result ->
                    if (result is Result.SuccessWithData) {
                        val members = mutableMapOf(
                            creatorId to true, *userIds.map { it to false }.toTypedArray()
                        )
                        val room = RemoteRoom(
                            name = groupName,
                            picUrl = result.data,
                            members = members,
                            isPrivate = false,
                            createdAt = nowLocal().format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                        )
                        val roomResult = supabase.from(ROOMS).insert(room) {
                            select()
                        }.decodeSingle<RemoteRoom>().toRoom()
                        createRecentRoom(roomResult.id, roomResult.name, roomResult.picUrl)
                        updateUsersRooms(userIds.plus(creatorId), creatorId, roomResult.id)
                        Result.SuccessWithData(roomResult)
                    } else {
                        Result.Error("Error uploading image: ${(result as Result.Error).message}")
                    }
                }
            } else {
                val members =
                    mutableMapOf(creatorId to true, *userIds.map { it to false }.toTypedArray())
                val room = RemoteRoom(
                    name = groupName,
                    members = members,
                    isPrivate = false,
                    createdAt = nowLocal().format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                )
                val roomResult = supabase.from(ROOMS).insert(room) {
                    select()
                }.decodeSingle<RemoteRoom>().toRoom()
                createRecentRoom(roomResult.id, roomResult.name, roomResult.picUrl)
                updateUsersRooms(userIds.plus(creatorId), creatorId, roomResult.id)
                Result.SuccessWithData(roomResult)
            }
        } catch (e: Exception) {
            Result.Error("Error creating group room: ${e.message}")
        }
    }

    override suspend fun addGroupMembers(roomId: Long, userIds: List<String>): Result<Unit> {
        return try {
            val room = supabase.from(ROOMS).select {
                filter {
                    eq("id", roomId)
                }
            }.decodeSingle<RemoteRoom>()
            supabase.from(ROOMS).update({
                set("members", room.members.plus(userIds.map { it to false }))
            }) {
                filter {
                    eq("id", roomId)
                }
            }
            updateUsersRooms(userIds, creatorId = "", roomId)
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error adding group members: ${e.message}")
        }
    }

    override suspend fun getRoom(roomId: Long): Result<Room> {
        return try {
            val room = supabase.from(ROOMS).select {
                filter {
                    eq("id", roomId)
                }
            }.decodeSingle<RemoteRoom>().toRoom()
            Result.SuccessWithData(room)
        } catch (e: Exception) {
            Result.Error("Error getting room: ${e.message}")
        }
    }

    override suspend fun getPrivateRoom(currentUser: User, otherUser: User): Result<Room> {
        return try {
            val userPrivateChats = supabase.from(USERROOMS).select {
                filter {
                    eq("userId", currentUser.id)
                }
            }.decodeSingle<UserRooms>().privateChats
            if (userPrivateChats.containsKey(otherUser.id)) {
                val room = supabase.from(ROOMS).select {
                    filter {
                        eq("id", userPrivateChats.getValue(otherUser.id))
                    }
                }.decodeSingle<RemoteRoom>().toRoom()
                Result.SuccessWithData(room)
            } else {
                val timestamp = nowLocal().format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                val remoteRoom = RemoteRoom(
                    name = "${currentUser.name} and ${otherUser.name} private chat",
                    members = mutableMapOf(currentUser.id to true, otherUser.id to true),
                    isPrivate = true,
                    createdAt = timestamp
                )
                val createdRoom = supabase.from(ROOMS).insert(remoteRoom) {
                    select()
                }.decodeSingle<RemoteRoom>()
                val remoteRecentRoom = RemoteRecentRoom(
                    roomId = createdRoom.id,
                    lastMessage = "No messages yet",
                    isPrivate = true,
                    senderId = currentUser.id,
                    senderName = currentUser.name,
                    senderPicUrl = currentUser.profilePictureURL,
                    receiverId = otherUser.id,
                    receiverName = otherUser.name,
                    receiverPicUrl = otherUser.profilePictureURL,
                    lastMessageTime = timestamp
                )
                supabase.from(RECENTROOMS).insert(remoteRecentRoom)
                updateUsersRooms(
                    listOf(currentUser.id, otherUser.id), creatorId = currentUser.id, createdRoom.id
                )
                updateUsersPrivateChats(currentUser.id, otherUser.id, createdRoom.id)
                updateUsersPrivateChats(otherUser.id, currentUser.id, createdRoom.id)
                Result.SuccessWithData(createdRoom.toRoom())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error getting private room: ${e.message}")
        }
    }

    override suspend fun removeMember(roomId: Long, userId: String): Result<Unit> {
        return try {
            val room = supabase.from(ROOMS).select {
                filter {
                    eq("id", roomId)
                }
            }.decodeSingle<RemoteRoom>()
            supabase.from(ROOMS).update({
                set("members", room.members.minus(userId))
            }) {
                filter {
                    eq("id", roomId)
                }
            }
            val userRooms = supabase.from(USERROOMS).select {
                filter {
                    eq("userId", userId)
                }
            }.decodeSingle<UserRooms>().rooms.minus(roomId)
            supabase.from(USERROOMS).update({
                set("rooms", userRooms)
            }) {
                filter {
                    eq("userId", userId)
                }
            }
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error removing member: ${e.message}")
        }
    }

    override suspend fun updateRoomAvatar(
        roomId: Long, newAvatarByteArray: ByteArray, newAvatarExtension: String
    ): Result<String> {
        return try {
            uploadGroupAvatar(
                newAvatarByteArray, roomId.toString(), newAvatarExtension
            ).let { result ->
                if (result is Result.SuccessWithData) {
                    supabase.from(ROOMS).update({
                        set("picUrl", result.data)
                    }) {
                        filter {
                            eq("id", roomId)
                        }
                    }
                    supabase.from(RECENTROOMS).update({
                        set("senderPicUrl", result.data)
                    }) {
                        filter {
                            eq("roomId", roomId)
                        }
                    }
                    Result.SuccessWithData(result.data)
                } else {
                    Result.Error("Error updating room avatar: ${(result as Result.Error).message}")
                }
            }
        } catch (e: Exception) {
            Result.Error("Error updating room avatar: ${e.message}")
        }
    }

    override suspend fun updateRoomName(roomId: Long, newName: String): Result<Unit> {
        return try {
            supabase.from(ROOMS).update({
                set("name", newName)
            }) {
                filter {
                    eq("id", roomId)
                }
            }
            supabase.from(RECENTROOMS).update({
                set("senderName", newName)
            }) {
                filter {
                    eq("roomId", roomId)
                }
            }
            Result.Success
        } catch (e: Exception) {
            Result.Error("Error updating room name: ${e.message}")
        }
    }

    private suspend fun updateUsersRooms(userIds: List<String>, creatorId: String, roomId: Long) {
        try {
            userIds.forEach { userId ->
                val userRooms = supabase.from(USERROOMS).select {
                    filter {
                        eq("userId", userId)
                    }
                }.decodeSingle<UserRooms>().rooms
                supabase.from(USERROOMS).update({
                    set("rooms", userRooms + roomId)
                }) {
                    filter {
                        eq("userId", userId)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun updateUsersPrivateChats(
        firstUserId: String, secondUserId: String, roomId: Long
    ) {
        try {
            val firstUserPrivateChats = supabase.from(USERROOMS).select {
                filter {
                    eq("userId", firstUserId)
                }
            }.decodeSingle<UserRooms>()
            supabase.from(USERROOMS).update({
                set(
                    "privateChats",
                    firstUserPrivateChats.privateChats + mapOf(secondUserId to roomId)
                )
            }) {
                filter {
                    eq("userId", firstUserId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun createRecentRoom(roomId: Long, roomName: String, roomPicUrl: String) {
        try {
            val room = RemoteRecentRoom(
                roomId = roomId,
                lastMessage = "No messages yet",
                isPrivate = false,
                senderName = roomName,
                senderPicUrl = roomPicUrl,
                lastMessageTime = nowLocal().format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
            )
            supabase.from(RECENTROOMS).insert(room)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun uploadGroupAvatar(
        avatarByteArray: ByteArray, groupName: String, imageExtension: String
    ): Result<String> {
        return try {
            val now = LocalDateTime.now().toInstant(TimeZone.UTC)
            val path = "${groupName.first()}/$groupName-${now.epochSeconds}.${imageExtension}"
            val bucket = supabase.storage.from("avatars")
            val key = bucket.upload(path, avatarByteArray, upsert = true)
            println("Key: $key")
            val url = supabase.storage.from("avatars").publicUrl(path)
            Result.SuccessWithData(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "An unknown error occurred while uploading group avatar")
        }
    }
}