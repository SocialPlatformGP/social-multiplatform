package com.gp.socialapp.data.chat.source.remote.model.response

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import kotlinx.serialization.Serializable

sealed class RecentRoomResponses {
    @Serializable
    data class GetAllRecentRooms(
        val recentRooms: List<RecentRoomResponse>
    ) : RecentRoomResponses()

}