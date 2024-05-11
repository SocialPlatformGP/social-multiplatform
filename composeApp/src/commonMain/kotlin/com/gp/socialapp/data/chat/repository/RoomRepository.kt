package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result

interface RoomRepository {
    suspend fun createGroupRoom(
        groupName: String,
        groupAvatarByteArray: ByteArray,
        groupAvatarExtension: String,
        userIds: List<String>,
        creatorId: String,
    ): Result<Room,ChatError>

    suspend fun addGroupMembers(
        roomId: Long,
        userIds: List<String>,
    ): Result<Unit,ChatError>

    suspend fun getRoom(
        roomId: Long
    ): Result<Room,ChatError>

    suspend fun removeMember(
        roomId: Long,
        userId: String
    ): Result<Unit,ChatError>

    suspend fun updateRoomAvatar(
        roomId: Long,
        newAvatarByteArray: ByteArray,
        newAvatarExtension: String,
    ): Result<String,ChatError>

    suspend fun getPrivateRoom(
        currentUser: User,
        otherUser: User
    ): Result<Room,ChatError>

    suspend fun updateRoomName(
        roomId: Long,
        newName: String
    ): Result<Unit,ChatError>
}