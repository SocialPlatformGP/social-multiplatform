package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import com.gp.socialapp.presentation.chat.home.ChatHomeUiState

@Composable
actual fun ExpandedChatHome(
    modifier: Modifier,
    startingChatRecentRoom: RecentRoom,
    recentRooms: List<RecentRoom>,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit
) {
    CompactChatHome( recentRooms = recentRooms, currentUserId = currentUserId, event = event)
}