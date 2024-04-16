package com.gp.socialapp.presentation.chat.creategroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.components.ChooseGroupMembersSection
import com.gp.socialapp.presentation.chat.creategroup.components.GroupAvatarSection
import com.gp.socialapp.presentation.chat.creategroup.components.GroupNameSection

object CreateGroupScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreateGroupScreenModel>()
        val state by screenModel.uiState.collectAsState()
        if (state.isCreated) {
            navigator.replace(
                ChatRoomScreen(
                    roomId = state.groupId,
                    roomTitle = state.groupName,
                    roomAvatarUrl = state.groupAvatarUrl,
                    isPrivate = false
                )
            )
            screenModel.resetState()
        }
        CreateGroupScreenContent(
            onAction = { action -> screenModel.handleUiAction(action) },
            groupName = state.groupName,
            avatarByteArray = state.groupAvatarByteArray,
            isError = state.isError,
            selectedUsers = state.selectedUsers,
            allUsers = state.allUsers,
        )
    }

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
                        GroupAvatarSection(
                            avatarByteArray = avatarByteArray,
                            isModifiable = true,
                            onImagePicked = { array ->
                                onAction(
                                    CreateGroupAction.OnImagePicked(array)
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        GroupNameSection(name = groupName,
                            onUpdateName = { name -> onAction(CreateGroupAction.OnUpdateName(name)) },
                            isError = isError,
                            onChangeError = { value -> onAction(CreateGroupAction.OnSetError(value)) })
                        ChooseGroupMembersSection(
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