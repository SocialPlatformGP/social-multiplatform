package com.gp.socialapp.presentation.chat.home

import com.gp.socialapp.data.chat.model.RecentRoomResponse

sealed class ChatHomeUiEvent {
    data class OnRecentChatClick(val recentRoomResponse: RecentRoomResponse) : ChatHomeUiEvent()
    data object OnCreateGroupClick : ChatHomeUiEvent()
    data object OnCreatePrivateChatClick : ChatHomeUiEvent()
    data object OnSearchClick : ChatHomeUiEvent()
    data object OnBackClick : ChatHomeUiEvent()

}