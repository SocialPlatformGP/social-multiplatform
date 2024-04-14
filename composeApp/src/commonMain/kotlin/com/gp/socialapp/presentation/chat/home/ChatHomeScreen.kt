package com.gp.socialapp.presentation.chat.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.chat.model.RecentRoomResponse
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreen
import com.gp.socialapp.presentation.chat.private_chat.CreatePrivateChatScreen
import com.seiko.imageloader.ui.AutoSizeImage

object ChatHomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<ChatHomeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        ChatHomeScreenContent(
            state
        ) { event ->
            when (event) {
                is ChatHomeUiEvent.OnRecentChatClick -> {
                    navigator.push(
                        ChatRoomScreen(
                            event.recentRoomResponse.roomId, event.recentRoomResponse.isPrivate
                        )
                    )
                }

                ChatHomeUiEvent.OnCreateGroupClick -> {
                    navigator.push(CreateGroupScreen)
                }

                ChatHomeUiEvent.OnCreatePrivateChatClick -> {
                    navigator.push(CreatePrivateChatScreen)
                }

                ChatHomeUiEvent.OnBackClick -> {
                    navigator.pop()
                }

                else -> Unit

            }

        }
    }
}

@Composable
fun ChatHomeScreenContent(
    state: ChatHomeUiState, event: (ChatHomeUiEvent) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.recentRooms) { recentRoom ->
                    val animatable = remember { Animatable(0.5f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
                    }
                    RecentRoomItem(
                        modifier = Modifier.graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        }, recentRoom, event = event
                    )
                }
            }
        }
    }
}

@Composable
fun RecentRoomItem(
    modifier: Modifier = Modifier,
    recentRoom: RecentRoomResponse,
    event: (ChatHomeUiEvent) -> Unit

) {
    Row(
        modifier = modifier.fillMaxWidth().padding(4.dp).clickable {
            event(ChatHomeUiEvent.OnRecentChatClick(recentRoom))
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        AutoSizeImage(
            "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
            contentDescription = "room image",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    text = recentRoom.title
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = recentRoom.lastMessageTime.toString(),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Row(
            ) {
                Text(
                    text = recentRoom.lastMessage,
                )
            }
        }
    }

}




