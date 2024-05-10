package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.util.Result

interface RoomRemoteDataSource {
    suspend fun createGroupRoom(
        groupName: String,
        groupAvatarByteArray: ByteArray,
        groupAvatarExtension: String,
        userIds: List<String>,
        creatorId: String,
    ): Result<Room>

    suspend fun addGroupMembers(
        roomId: Long,
        userIds: List<String>,
    ): Result<Unit>

    suspend fun getRoom(
        roomId: Long
    ): Result<Room>

    suspend fun getPrivateRoom(
        currentUser: User,
        otherUser: User
    ): Result<Room>

    suspend fun removeMember(
        roomId: Long,
        userId: String
    ): Result<Unit>

    suspend fun updateRoomAvatar(
        roomId: Long,
        newAvatarByteArray: ByteArray,
        newAvatarExtension: String,
    ): Result<String>

    suspend fun updateRoomName(
        roomId: Long,
        newName: String
    ): Result<Unit>
}