package com.gp.socialapp.presentation.chat.home

import com.gp.socialapp.data.chat.model.RecentRoomResponse

data class ChatHomeUiState(
    val recentRooms: List<RecentRoomResponse> = emptyList(),
)


