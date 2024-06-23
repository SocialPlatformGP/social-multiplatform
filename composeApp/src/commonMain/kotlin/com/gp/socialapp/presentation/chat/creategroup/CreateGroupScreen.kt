package com.gp.socialapp.presentation.chat.creategroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.components.ChooseGroupMembersSection
import com.gp.socialapp.presentation.chat.creategroup.components.GroupNameSection
import com.gp.socialapp.presentation.chat.creategroup.components.ModifiableAvatarSection
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.getPlatform

object CreateGroupScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CreateGroupScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.init()
            },
            onDisposed = {
                screenModel.onDispose()
            }
        )
        val platform = getPlatform()
        if (state.isCreated) {
            when(platform){
                Platform.ANDROID -> {
                    navigator.replace(
                        ChatRoomScreen(
                            roomId = state.groupId,
                            roomTitle = state.groupName,
                            roomAvatarUrl = state.groupAvatarUrl,
                            isPrivate = false
                        )
                    )
                }
                Platform.JVM -> {
                    val recentRoom = RecentRoom(
                        isPrivate = false,
                        roomId = state.groupId,
                        senderName = state.groupName,
                        senderPicUrl = state.groupAvatarUrl
                    )
                    navigator.push(
                        ChatHomeScreen(
                            startingChatRecentRoom = recentRoom
                        )
                    )
                }
                else -> Unit
            }

        }
        CreateGroupScreenContent(
            onAction = {
                when (it) {
                    is CreateGroupAction.OnBackClicked -> {
                        navigator.pop()
                    }

                    else -> screenModel.handleUiAction(it)
                }
            },
            groupName = state.groupName,
            avatarByteArray = state.groupAvatarByteArray,
            isError = state.isError,
            selectedUsers = state.selectedUsers,
            allUsers = state.allUsers,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateGroupScreenContent(
        modifier: Modifier = Modifier,
        onAction: (CreateGroupAction) -> Unit,
        groupName: String,
        avatarByteArray: ByteArray,
        isError: Boolean,
        selectedUsers: List<User>,
        allUsers: List<SelectableUser>,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onAction(CreateGroupAction.OnBackClicked)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {},
                )
            },
            modifier = modifier
        ) {
            Surface(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxSize().padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        ModifiableAvatarSection(
                            avatarByteArray = avatarByteArray,
                            isModifiable = true,
                            onImagePicked = { array, extension ->
                                onAction(
                                    CreateGroupAction.OnImagePicked(array, extension)
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        GroupNameSection(name = groupName,
                            onUpdateName = { name -> onAction(CreateGroupAction.OnUpdateName(name)) },
                            isError = isError,
                            onChangeError = { value -> onAction(CreateGroupAction.OnSetError(value)) })
                        ChooseGroupMembersSection(
                            modifier = Modifier.weight(1f),
                            selectedUsers = selectedUsers,
                            onUnselectUser = { userId ->
                                onAction(
                                    CreateGroupAction.OnUnselectUser(userId)
                                )
                            },
                            users = allUsers,
                            onUserClick = { selectableUser ->
                                println("User Clicked: ${selectableUser.user.email}")
                                onAction(
                                    if (selectableUser.isSelected) {
                                        CreateGroupAction.OnUnselectUser(selectableUser.user.id)
                                    } else {
                                        CreateGroupAction.OnSelectUser(selectableUser.user.id)
                                    }
                                )
                            })
                        Button(
                            onClick = {
                                onAction(CreateGroupAction.OnSetError(groupName.isBlank()))
                                if (groupName.isNotBlank()) {
                                    onAction(CreateGroupAction.OnCreateGroup)
                                }
                            },
                            shape = RoundedCornerShape(32.dp),
                            modifier = Modifier.fillMaxWidth().padding(8.dp).height(54.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        ) {
                            Text(
                                text = "Create Group",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}