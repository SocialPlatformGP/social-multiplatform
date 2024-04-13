package com.gp.socialapp.data.chat.source.remote.model.request

sealed class RoomRequest {
    data class CreateGroupRoom(
        val groupName: String,
        val groupAvatar: ByteArray,
        val userIds: List<String>,
        val creatorId: String
    ): RoomRequest()
}