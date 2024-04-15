package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRemoteDataSource {


    suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<String>>
    suspend fun checkIfRoomExists(user1: String, user2: String): Flow<Result<Room?>>
}