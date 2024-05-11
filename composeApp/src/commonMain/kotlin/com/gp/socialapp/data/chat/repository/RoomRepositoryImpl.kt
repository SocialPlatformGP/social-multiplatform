package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result

class RoomRepositoryImpl(
    private val remoteDataSource: RoomRemoteDataSource
) : RoomRepository {
    override suspend fun createGroupRoom(
        groupName: String,
        groupAvatarByteArray: ByteArray,
        groupAvatarExtension: String,
        userIds: List<String>,
        creatorId: String
    ): Result<Room,ChatError> {
        return remoteDataSource.createGroupRoom(
            groupName,
            groupAvatarByteArray,
            groupAvatarExtension,
            userIds,
            creatorId
        )
    }

    override suspend fun addGroupMembers(roomId: Long, userIds: List<String>): Result<Unit,ChatError> {
        return remoteDataSource.addGroupMembers(roomId, userIds)
    }

    override suspend fun getRoom(roomId: Long): Result<Room,ChatError> {
        return remoteDataSource.getRoom(roomId)
    }

    override suspend fun removeMember(roomId: Long, userId: String): Result<Unit,ChatError> {
        return remoteDataSource.removeMember(roomId, userId)
    }

    override suspend fun updateRoomAvatar(
        roomId: Long,
        newAvatarByteArray: ByteArray,
        newAvatarExtension: String
    ): Result<String,ChatError> {
        return remoteDataSource.updateRoomAvatar(roomId, newAvatarByteArray, newAvatarExtension)
    }

    override suspend fun getPrivateRoom(currentUser: User, otherUser: User): Result<Room,ChatError> {
        return remoteDataSource.getPrivateRoom(currentUser, otherUser)
    }

    override suspend fun updateRoomName(roomId: Long, newName: String): Result<Unit,ChatError> {
        return remoteDataSource.updateRoomName(roomId, newName)
    }

}