package com.gp.socialapp.data.chat.source.remote.model.request

import com.gp.socialapp.data.chat.model.Room

sealed class RoomRequest {
    @kotlinx.serialization.Serializable
    data class CreateGroupRoom(
        val groupName: String,
        val groupAvatar: ByteArray,
        val userIds: List<String>,
        val creatorId: String
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class CreateRoomRequest(
        val room: Room
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class GetRoomRequest(
        val roomId: String,
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class RoomExistRequest(
        val senderId: String,
        val receiverId: String,
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class GetRoomDetails(
        val roomId: String
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class UpdateRoomAvatar(
        val roomId: String,
        val byteArray: ByteArray
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class UpdateRoomName(
        val roomId: String,
        val name: String
    ) : RoomRequest()

    @kotlinx.serialization.Serializable
    data class AddMembers(
        val roomId: String,
        val userIds: List<String>
    )

    data class RemoveMember(
        val roomId: String,
        val userId: String
    )

}