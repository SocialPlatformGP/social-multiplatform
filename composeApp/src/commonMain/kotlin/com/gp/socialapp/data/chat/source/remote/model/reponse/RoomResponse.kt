package com.gp.socialapp.data.chat.source.remote.model.reponse

sealed class RoomResponse {
    data class CreateGroupRoom(
        val roomId: String
    ): RoomResponse()
}