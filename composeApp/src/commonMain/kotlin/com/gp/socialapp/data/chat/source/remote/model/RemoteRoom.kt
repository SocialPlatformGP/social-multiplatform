package com.gp.socialapp.data.chat.source.remote.model

import com.gp.socialapp.data.chat.model.Room
import korlibs.time.DateFormat
import korlibs.time.DateTimeTz
import korlibs.time.parse
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRoom(
    val id: Long = 0L,
    val name: String = "",
    val picUrl: String = "",
    val members: Map<String,Boolean> = emptyMap(),
    val isPrivate: Boolean = true,
    val createdAt: String = "",
    val bio: String = "",
) {
    fun toRoom(): Room {
        val formatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
        val createdAt = formatter.parse(this.createdAt)
        return Room(
            id = id,
            name = name,
            picUrl = picUrl,
            members = members,
            isPrivate = isPrivate,
            createdAt = createdAt,
            bio = bio
        )
    }
    companion object {
        fun fromRoom(room: Room): RemoteRoom {
            val createdAt = room.createdAt.format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
            return RemoteRoom(
                id = room.id,
                name = room.name,
                picUrl = room.picUrl,
                members = room.members,
                isPrivate = room.isPrivate,
                createdAt = createdAt,
                bio = room.bio
            )
        }
    }
}
