package com.gp.socialapp.presentation.chat.home

import com.gp.socialapp.data.chat.model.RecentRoom

sealed interface ChatHomeUiEvent {
    data class OnRecentChatClick(val recentRoom: RecentRoom) : ChatHomeUiEvent
    data object OnCreateGroupClick : ChatHomeUiEvent
    data object OnCreateChatClick : ChatHomeUiEvent
    data object OnBackClick : ChatHomeUiEvent

}