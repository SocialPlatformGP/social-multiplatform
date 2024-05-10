package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent

@Composable
fun FabWithOptionButtons(
    fabState: MutableState<Boolean>, event: (ChatHomeUiEvent) -> Unit
) {
    RectangleFab(
        fabState, { event(ChatHomeUiEvent.OnCreateGroupClick) }, "Create Group"
    )
    RectangleFab(
        fabState, { event(ChatHomeUiEvent.OnCreatePrivateChatClick) }, "Create Private Chat"
    )
    SingleFab(
        fabState, Icons.Default.Cancel
    )
}