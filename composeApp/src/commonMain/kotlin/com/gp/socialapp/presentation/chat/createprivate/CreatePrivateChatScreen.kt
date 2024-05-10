package com.gp.socialapp.presentation.chat.createprivate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.createprivate.components.CreatePrivateChatItem
import com.gp.socialapp.presentation.chat.createprivate.components.CreatePrivateChatSearchBar
import com.gp.socialapp.presentation.chat.createprivate.components.CreatePrivateChatTopBar

object CreatePrivateChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreatePrivateChatScreenModel>()
        val state by screenModel.uiState.collectAsState()

        state.room?.let {
            navigator.push(
                ChatRoomScreen(
                    roomId = it.id, isPrivate = true, roomAvatarUrl = it.picUrl, roomTitle = it.name
                )
            )
            screenModel.clear()
        }
        CreatePrivateChatContent(users = state.matchingUsers,
            onUserSelected = { user ->
                screenModel.onUserSelected(user)
            },
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            onSearchQuerySubmitted = { },
            onBackPressed = navigator::pop
        )
    }

    @Composable
    private fun CreatePrivateChatContent(
        users: List<User>,
        onSearchQueryChanged: (String) -> Unit,
        onSearchQuerySubmitted: () -> Unit,
        onUserSelected: (User) -> Unit,
        onBackPressed: () -> Unit
    ) {
        var isSearchBarVisible by remember { mutableStateOf(false) }
        Scaffold(topBar = {
            if (isSearchBarVisible) {
                CreatePrivateChatSearchBar(
                    onSearchQueryChange = onSearchQueryChanged, onSearchQuerySubmit = {
                        isSearchBarVisible = false
                        onSearchQuerySubmitted()
                    }, onBackPressed = onBackPressed
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
