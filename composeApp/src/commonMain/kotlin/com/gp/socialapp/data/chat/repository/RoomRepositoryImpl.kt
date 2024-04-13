package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl (
    private val remoteDataSource: RoomRemoteDataSource
): RoomRepository{
    override suspend fun createGroupRoom(
        groupName: String,
        groupAvatar: ByteArray,
        userIds: List<String>,
        creatorId: String
    ): Flow<Result<String>> {
        val request = RoomRequest.CreateGroupRoom(groupName, groupAvatar, userIds, creatorId)
        return remoteDataSource.createGroupRoom(request)
    }
}