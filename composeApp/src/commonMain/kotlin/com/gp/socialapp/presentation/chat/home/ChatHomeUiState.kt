package com.gp.socialapp.presentation.chat.home

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.RecentRoom

data class ChatHomeUiState(
    val recentRooms: List<RecentRoom> = emptyList(), val currentUser: User = User()

)


