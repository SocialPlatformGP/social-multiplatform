package com.gp.socialapp.data.chat.source.remote.model.response

import com.gp.socialapp.data.chat.model.Room
import kotlinx.serialization.Serializable

sealed class RoomResponse {
    @Serializable
    data class CreateGroupRoom(
        val roomId: String,
        val roomAvatarUrl: String,
    ) : RoomResponse()

    @Serializable
    data class CheckIfRoomExists(
        val room: Room?
    ) : RoomResponse()

    @Serializable
    data class GetRoomDetails(
        val room: Room
    ) : RoomResponse()

    @Serializable
    data class UpdateRoomAvatar(
        val avatarUrl: String
    ) : RoomResponse()
}