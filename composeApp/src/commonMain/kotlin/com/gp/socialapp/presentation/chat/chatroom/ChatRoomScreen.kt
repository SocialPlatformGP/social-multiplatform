package com.gp.socialapp.presentation.chat.chatroom

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.gp.socialapp.presentation.chat.chatroom.components.ChatRoomTopBar

data class ChatRoomScreen(
    private val roomId: String, private val isPrivate: Boolean
) : Screen {
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }

    @Composable
    private fun ChatRoomContent(
        modifier: Modifier = Modifier,
    ) {
        val scrollState = rememberLazyListState()
        var isImagePreviewDialogOpen by rememberSaveable { mutableStateOf(false) }
        var previewedImageURL by rememberSaveable { mutableStateOf("") }
        var isEditMessageDialogOpen by rememberSaveable { mutableStateOf(false) }
        var EditedMessageID by remember { mutableStateOf("") }
        var EditedMessageBody by remember { mutableStateOf("") }
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            topBar = {
                ChatRoomTopBar(
                    onBackPressed = { /*TODO*/ },
                    onChatHeaderClicked = { /*TODO*/ },
                    isPrivateChat = isPrivate,
                    chatImageURL = "",
                    chatTitle = ""
                )
            },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
                .exclude(WindowInsets.ime),
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                }
            }
        }
    }
}
