package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import com.gp.socialapp.presentation.chat.home.ChatHomeUiState

@Composable
expect fun ExpandedChatHome(
    modifier: Modifier = Modifier,
    startingChatRecentRoom: RecentRoom,
    recentRooms: List<RecentRoom>,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit,
)