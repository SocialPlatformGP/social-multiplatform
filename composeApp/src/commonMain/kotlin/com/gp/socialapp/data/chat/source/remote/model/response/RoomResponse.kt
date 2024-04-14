package com.gp.socialapp.data.chat.source.remote.model.response

import com.gp.socialapp.data.chat.model.Room

sealed class RoomResponse {
    @kotlinx.serialization.Serializable
    data class CreateRoomResponse(
        val room: Room
    ) : RoomResponse()

    @kotlinx.serialization.Serializable
    data class GetRoomResponse(
        val room: Room
    ) : RoomResponse()

    @kotlinx.serialization.Serializable
    data class RoomExistResponse(
        val exist: Boolean,
        val roomId: String
    ) : RoomResponse()

}