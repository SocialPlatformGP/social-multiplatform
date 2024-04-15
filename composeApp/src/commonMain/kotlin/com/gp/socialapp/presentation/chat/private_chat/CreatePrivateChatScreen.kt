package com.gp.socialapp.presentation.chat.private_chat

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.seiko.imageloader.ui.AutoSizeImage

object CreatePrivateChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreatePrivateChatScreenModel>()
        val state by screenModel.uiState.collectAsState()

        state.room?.let {
            navigator.push(
                ChatRoomScreen(
                    roomId = it.id,
                    isPrivate = true,
                    roomAvatarUrl = it.picUrl,
                    roomTitle = it.name
                )
            )
            screenModel.clear()

        }

        CreatePrivateChatContent(state) { user ->
            screenModel.onUserSelected(user)
        }

    }

    @Composable
    private fun CreatePrivateChatContent(
        state: CreatePrivateChatUiState,
        onUserSelected: (User) -> Unit
    ) {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            ) {
                LazyColumn {
                    items(state.users) { user ->
                        CreatePrivateChatItem(user) {
                            onUserSelected(user)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CreatePrivateChatItem(
        user: User,
        OnUserSelected: () -> Unit
    ) {
        Row(
            Modifier.fillMaxWidth().padding(8.dp).clickable {
                OnUserSelected()
            },
        ) {
            AutoSizeImage(
                url = user.profilePictureURL,
                contentDescription = "Profile Picture",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Text(
                text = user.firstName + " " + user.lastName,
                modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically)
            )
        }

    }
}