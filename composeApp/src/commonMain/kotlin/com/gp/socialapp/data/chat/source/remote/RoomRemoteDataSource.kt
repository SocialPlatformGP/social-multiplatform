package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRemoteDataSource {
    suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<String>>
}