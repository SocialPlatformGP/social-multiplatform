package com.gp.socialapp.presentation.chat.home

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreen
import com.gp.socialapp.presentation.chat.createchat.CreateChatScreen
import com.gp.socialapp.presentation.chat.home.components.CompactChatHome
import com.gp.socialapp.presentation.chat.home.components.ExpandedChatHome
import io.github.aakira.napier.Napier

data class ChatHomeScreen(val startingChatRecentRoom: RecentRoom = RecentRoom()) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val windowSizeClass = calculateWindowSizeClass()
        val screenModel = rememberScreenModel<ChatHomeScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.onDispose() }
        )
        val state by screenModel.uiState.collectAsState()
        val onEvent: (ChatHomeUiEvent) -> Unit ={ event ->
            when (event) {
                is ChatHomeUiEvent.OnRecentChatClick -> {
                    event.recentRoom.let {
                        navigator.pop()
                        val screen = if (it.isPrivate) {
                            val roomTitle =
                                if (it.senderId == state.currentUser.id) it.receiverName else it.senderName
                            val roomPic =
                                if (it.senderId == state.currentUser.id) it.receiverPicUrl else it.senderPicUrl
                            ChatRoomScreen(it.roomId,  roomPic, roomTitle, it.isPrivate)
                        } else {
                            ChatRoomScreen(it.roomId, it.senderPicUrl, it.senderName, it.isPrivate)
                        }
                        navigator.push(screen)
                    }

                }

                ChatHomeUiEvent.OnCreateGroupClick -> {
                    navigator.push(CreateGroupScreen)
                }

                ChatHomeUiEvent.OnCreateChatClick -> {
                    navigator.push(CreateChatScreen)
                }

                ChatHomeUiEvent.OnBackClick -> {
                    screenModel.onDispose()
                    navigator.pop()
                }

                else -> Unit
            }

        }
        when(windowSizeClass.widthSizeClass){
            WindowWidthSizeClass.Compact -> {
                CompactChatHome(
                    recentRooms = state.recentRooms,
                    currentUserId = state.currentUser.id,
                    event = onEvent
                )
            }
            else -> {
                ExpandedChatHome(
                    recentRooms = state.recentRooms,
                    currentUserId = state.currentUser.id,
                    event = onEvent,
                    startingChatRecentRoom = startingChatRecentRoom
                )
            }
        }
    }
}




