package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl(
    private val remoteDataSource: RoomRemoteDataSource
) : RoomRepository {
    override suspend fun createGroupRoom(
        groupName: String,
        groupAvatar: ByteArray,
        userIds: List<String>,
        creatorId: String
    ): Flow<Result<Room,ChatError.Temp>> {
        val request = RoomRequest.CreateGroupRoom(groupName, groupAvatar, userIds, creatorId)
        return remoteDataSource.createGroupRoom(request)
    }

    override suspend fun checkIfRoomExists(
        user1: String,
        user2: String
    ) = remoteDataSource.checkIfRoomExists(user1, user2)

    override suspend fun getRoomDetails(roomId: String): Result<Room,ChatError.Temp> {
        val request = RoomRequest.GetRoomDetails(roomId)
        return remoteDataSource.getRoomDetails(request)
    }

    override suspend fun updateRoomAvatar(roomId: String, byteArray: ByteArray): Result<String,ChatError.Temp> {
        val request = RoomRequest.UpdateRoomAvatar(roomId, byteArray)
        return remoteDataSource.updateRoomAvatar(request)
    }

    override suspend fun updateRoomName(roomId: String, name: String): Result<Unit,ChatError.Temp> {
        val request = RoomRequest.UpdateRoomName(roomId, name)
        return remoteDataSource.updateRoomName(request)
    }

    override suspend fun addMembers(roomId: String, userIds: List<String>): Result<Unit,ChatError.Temp> {
        val request = RoomRequest.AddMembers(roomId, userIds)
        return remoteDataSource.addMembers(request)
    }

    override suspend fun removeMember(roomId: String, userId: String): Result<Unit,ChatError.Temp> {
        val request = RoomRequest.RemoveMember(roomId, userId)
        return remoteDataSource.removeMember(request)
    }
}