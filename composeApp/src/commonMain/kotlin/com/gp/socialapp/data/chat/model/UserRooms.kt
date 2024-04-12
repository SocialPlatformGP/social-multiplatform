package com.gp.socialapp.data.chat.model

@kotlinx.serialization.Serializable
data class UserRooms(
    val userId :String = "",
    val roomsIds :List<String> = emptyList(), //roomIds
)