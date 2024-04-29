package com.gp.socialapp.presentation.chat.groupdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.popUntil
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.addmembers.AddMembersScreen
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.components.GroupAvatarSection
import com.gp.socialapp.presentation.chat.groupdetails.components.GroupDetailsNameSection
import com.gp.socialapp.presentation.chat.groupdetails.components.GroupMembersSection
import com.gp.socialapp.presentation.chat.groupdetails.components.RemoveMemberAlertDialog
import com.gp.socialapp.presentation.chat.groupdetails.components.UserClickedDialog
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.confirm_member_removal

data class GroupDetailsScreen(
    private val roomId: String,
    private val roomTitle: String,
    private val roomAvatarUrl: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<GroupDetailsScreenModel>()
        LifecycleEffect(
            onStarted = {
                screenModel.init(roomId, roomTitle, roomAvatarUrl)
            },
            onDisposed = {
                screenModel.dispose()
            }
        )
        val state by screenModel.uiState.collectAsState()
        if(state.privateRoom != null) {
            navigator.push(ChatRoomScreen(
                roomId = state.privateRoom!!.id,
                roomTitle = state.privateRoom!!.name,
                roomAvatarUrl = state.privateRoom!!.picUrl,
                isPrivate = true
            ))
            navigator.popUntil { it is ChatHomeScreen }
        } else {
            GroupDetailsContent(
                avatarURL = state.groupAvatarUrl,
                isAdmin = state.isAdmin,
                onAction = { action ->
                    when (action) {
                        is GroupDetailsAction.OnAddMembersClicked -> {
                            navigator.push(
                                AddMembersScreen(
                                    roomId = roomId,
                                    groupMembersIds = state.members.map { it.id })
                            )
                        }

                        is GroupDetailsAction.OnViewUserProfile -> {
                            //todo navigate to user profile
                        }

                        is GroupDetailsAction.OnBackClicked -> {
                            navigator.pop()
                        }

                        else -> {
                            screenModel.handleUiAction(action)
                        }
                    }
                },
                name = state.groupName,
                members = state.members,
                admins = state.admins,
                currentUserId = state.currentUserId,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun GroupDetailsContent(
        modifier: Modifier = Modifier,
        avatarURL: String,
        isAdmin: Boolean,
        onAction: (GroupDetailsAction) -> Unit,
        name: String,
        members: List<User>,
        admins: List<String>,
        currentUserId: String,
    ) {
        var clickedUserId by remember { mutableStateOf("") }
        var isUserClickedDialogOpen by remember { mutableStateOf(false) }
        var isRemoveMemberDialogOpen by rememberSaveable { mutableStateOf(false) }
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onAction(GroupDetailsAction.OnBackClicked)
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
        ) {
            Surface(
                modifier = Modifier.padding(it),
                color = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    GroupAvatarSection(
                        avatarURL = avatarURL,
                        isModifiable = isAdmin,
                        onImagePicked = { array ->
                            onAction(GroupDetailsAction.OnChangeAvatar(array))
                        }
                    )
                    GroupDetailsNameSection(
                        name = name,
                        isModifiable = isAdmin,
                        onChangeName = { newName ->
                            onAction(GroupDetailsAction.OnChangeName(newName))
                        }
                    )
                    HorizontalDivider(modifier = Modifier.height(2.dp))
                    GroupMembersSection(
                        members = members,
                        admins = admins,
                        isAdmin = isAdmin,
                        onAddMembersClicked = { onAction(GroupDetailsAction.OnAddMembersClicked) },
                        onUserClicked = {
                            clickedUserId = it
                            isUserClickedDialogOpen = true
                        }
                    )
                }
                if (isRemoveMemberDialogOpen) {
                    RemoveMemberAlertDialog(
                        onDismissRequest = { isRemoveMemberDialogOpen = false },
                        onConfirmation = {
                            onAction(GroupDetailsAction.OnRemoveMember(clickedUserId))
                            isRemoveMemberDialogOpen = false
                        },
                        dialogTitle = stringResource(resource = Res.string.confirm_member_removal)
                    )
                }
                if (isUserClickedDialogOpen) {
                    UserClickedDialog(
                        isAdmin = isAdmin,
                        isCurrentUser = clickedUserId == currentUserId,
                        userId = clickedUserId,
                        onRemoveMember = {
                            clickedUserId = it
                            isRemoveMemberDialogOpen = true
                            isUserClickedDialogOpen = false
                        },
                        onMessageUser = {
                            onAction(GroupDetailsAction.OnMessageUser(it))
                        },
                        onDismiss = { isUserClickedDialogOpen = false },
                        onViewProfile = {
                            onAction(GroupDetailsAction.OnViewUserProfile(it))
                        }
                    )
                }
            }
        }
    }
}
