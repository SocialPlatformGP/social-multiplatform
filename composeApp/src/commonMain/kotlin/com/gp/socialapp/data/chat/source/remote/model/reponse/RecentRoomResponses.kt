package com.gp.socialapp.data.chat.source.remote.model.reponse

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import kotlinx.serialization.Serializable

sealed class RecentRoomResponses {
    @Serializable
    data class GetAllRecentRooms(
        val recentRooms: List<RecentRoomResponse>
    ) : RecentRoomResponses()

}