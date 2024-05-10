package com.gp.socialapp.presentation.chat.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreen
import com.gp.socialapp.presentation.chat.createprivate.CreatePrivateChatScreen
import com.gp.socialapp.presentation.chat.home.components.FabWithOptionButtons
import com.gp.socialapp.presentation.chat.home.components.RecentRoomItem
import com.gp.socialapp.presentation.chat.home.components.SingleFab

object ChatHomeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<ChatHomeScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.onDispose() }
        )
        val state by screenModel.uiState.collectAsState()
        ChatHomeScreenContent(
            state
        ) { event ->
            when (event) {
                is ChatHomeUiEvent.OnRecentChatClick -> {
                    event.recentRoom.let {
                        navigator.pop()
                        val screen = if (it.isPrivate) {
                            val roomTitle =
                                if (it.senderId == state.currentUser.id) it.receiverName else it.senderName
                            val roomPic =
                                if (it.senderId == state.currentUser.id) it.receiverPicUrl else it.senderPicUrl
                            ChatRoomScreen(it.roomId, roomTitle, roomPic, it.isPrivate)
                        } else {
                            ChatRoomScreen(it.roomId, it.senderPicUrl, it.senderName, it.isPrivate)
                        }
                        navigator.push(screen)
                    }

                }

                ChatHomeUiEvent.OnCreateGroupClick -> {
                    navigator.push(CreateGroupScreen)
                }

                ChatHomeUiEvent.OnCreatePrivateChatClick -> {
                    navigator.push(CreatePrivateChatScreen)
                }

                ChatHomeUiEvent.OnBackClick -> {
                    screenModel.onDispose()
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
    Scaffold(floatingActionButton = {
        var fabState = remember { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Bottom
        ) {
            if (!fabState.value) SingleFab(
                fabState, Icons.Default.Add
            )
            else {
                FabWithOptionButtons(fabState, event)
            }
        }
    }

    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                println("recent rooms: ${state.recentRooms}")
                items(state.recentRooms) { recentRoom ->
                    val animatable = remember { Animatable(0.5f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
                    }
                    RecentRoomItem(
                        modifier = Modifier.graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        },
                        recentRoom = recentRoom,
                        currentUserId = state.currentUser.id,
                        event = event
                    )
                }
            }
        }
    }
}




