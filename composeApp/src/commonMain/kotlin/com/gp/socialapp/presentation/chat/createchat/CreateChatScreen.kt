package com.gp.socialapp.presentation.chat.createchat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.createchat.components.CreatePrivateChatItem
import com.gp.socialapp.presentation.chat.createchat.components.CreatePrivateChatSearchBar
import com.gp.socialapp.presentation.chat.createchat.components.CreatePrivateChatTopBar
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreen
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.getPlatform

object CreateChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreateChatScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val platform = getPlatform()
        LifecycleEffect(onStarted = {
            screenModel.init()
        }, onDisposed = {
            screenModel.onDispose()
        })
        if(state.room != null && state.recentRoom != null){
            when(platform){
                Platform.ANDROID -> {
                    val room = state.room!!
                    navigator.replace(
                        ChatRoomScreen(
                            roomId = room.id, isPrivate = true, roomAvatarUrl = room.picUrl, roomTitle = room.name
                        )
                    )
                }
                Platform.JVM -> {
                    navigator.replace(
                        ChatHomeScreen(
                            startingChatRecentRoom = state.recentRoom!!
                        )
                    )
                }
                else -> Unit
            }
        }
        CreatePrivateChatContent(users = state.matchingUsers,
            onUserSelected = { user ->
                screenModel.onUserSelected(user)
            },
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            onSearchQuerySubmitted = { },
            onBackPressed = navigator::pop,
            onCreateGroup = {
                navigator.push(CreateGroupScreen)
            }
        )
    }

    @Composable
    private fun CreatePrivateChatContent(
        users: List<User>,
        onSearchQueryChanged: (String) -> Unit,
        onSearchQuerySubmitted: () -> Unit,
        onUserSelected: (User) -> Unit,
        onCreateGroup: () -> Unit,
        onBackPressed: () -> Unit
    ) {
        var isSearchBarVisible by remember { mutableStateOf(false) }
        Scaffold(topBar = {
            if (isSearchBarVisible) {
                CreatePrivateChatSearchBar(
                    onSearchQueryChange = onSearchQueryChanged, onSearchQuerySubmit = {
                        isSearchBarVisible = false
                        onSearchQuerySubmitted()
                    }, onBackPressed = { isSearchBarVisible = false}
                )
            } else {
                CreatePrivateChatTopBar(onBackPressed = onBackPressed) {
                    isSearchBarVisible = true
                }

            }
        }) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            ) {
                LazyColumn {
                    item{
                        ListItem(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).clickable { onCreateGroup() },
                            headlineContent = {
                                Text("Create a group", fontSize = 18.sp)
                            },
                            leadingContent = {
                                Icon(imageVector = Icons.Default.GroupAdd, contentDescription = "Create a group",modifier = Modifier.size(48.dp))
                            },
                        )
                    }
                    items(users) { user ->
                        CreatePrivateChatItem(user) {
                            onUserSelected(user)
                        }
                    }
                }
            }
        }
    }


}
