package com.gp.socialapp.data.chat.source.remote.model.response

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.RecentRoomResponse

@kotlinx.serialization.Serializable
data class NewDataResponse(
    val messages: Message? = null,
    val recentRooms: List<RecentRoomResponse> = emptyList()
)