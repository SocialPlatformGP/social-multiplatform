package com.gp.socialapp.data.chat.source.remote.model.request

import kotlinx.serialization.Serializable

sealed class RecentRoomRequests {
    @Serializable
    data class GetAllRecentRooms(
        val userId: String
    ) : RecentRoomRequests()


}
