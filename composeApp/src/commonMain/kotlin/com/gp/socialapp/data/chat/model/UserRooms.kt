package com.gp.socialapp.data.chat.model

@kotlinx.serialization.Serializable
data class UserRooms(
    val userId: String = "",
    val rooms: List<Long> = emptyList(),
    val privateChats: Map<String, String> = emptyMap()
)