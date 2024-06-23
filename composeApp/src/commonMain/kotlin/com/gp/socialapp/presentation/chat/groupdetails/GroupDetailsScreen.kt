package com.gp.socialapp.presentation.chat.groupdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.addmembers.AddMembersScreen
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.creategroup.components.ModifiableAvatarSection
import com.gp.socialapp.presentation.chat.groupdetails.components.ConfirmActionAlertDialog
import com.gp.socialapp.presentation.chat.groupdetails.components.GroupDetailsNameSection
import com.gp.socialapp.presentation.chat.groupdetails.components.GroupMembersSection
import com.gp.socialapp.presentation.chat.groupdetails.components.UserClickedDialog
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import com.gp.socialapp.presentation.home.components.OptionItem
import com.gp.socialapp.presentation.userprofile.UserProfileScreen
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.getPlatform
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.confirm_member_removal

data class GroupDetailsScreen(
    private val roomId: Long, private val roomTitle: String, private val roomAvatarUrl: String
) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<GroupDetailsScreenModel>()
        LifecycleEffect(onStarted = {
            screenModel.init(roomId, roomTitle, roomAvatarUrl)
        }, onDisposed = {
            screenModel.onDispose()
        })
        val state by screenModel.uiState.collectAsState()
        val platform = getPlatform()
        val windowSizeClass = calculateWindowSizeClass()
        if (state.privateRoom != null && state.privateRecentRoom != null) {
            when (platform) {
                Platform.ANDROID -> {
                    val room = state.privateRoom!!
                    navigator.replace(
                        ChatRoomScreen(
                            roomId = room.id,
                            isPrivate = true,
                            roomAvatarUrl = room.picUrl,
                            roomTitle = room.name
                        )
                    )
                }

                Platform.JVM -> {
                    navigator.replace(
                        ChatHomeScreen(
                            startingChatRecentRoom = state.privateRecentRoom!!
                        )
                    )
                }

                else -> Unit
            }
            navigator.popUntil { it is ChatHomeScreen }
        } else {
            GroupDetailsContent(
                avatarURL = state.groupAvatarUrl,
                isAdmin = state.isAdmin,
                onAction = { action ->
                    when (action) {
                        is GroupDetailsAction.OnAddMembersClicked -> {
                            navigator.push(
                                AddMembersScreen(roomId = roomId,
                                    groupMembersIds = state.members.map { it.id })
                            )
                        }

                        is GroupDetailsAction.OnViewUserProfile -> {
                            navigator.push(UserProfileScreen(action.userId))
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
                currentUserId = state.currentUser.id,
                windowWidthSizeClass = windowSizeClass.widthSizeClass
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun GroupDetailsContent(
        modifier: Modifier = Modifier,
        windowWidthSizeClass: WindowWidthSizeClass,
        avatarURL: String,
        isAdmin: Boolean,
        onAction: (GroupDetailsAction) -> Unit,
        name: String,
        members: List<User>,
        admins: List<String>,
        currentUserId: String,
    ) {
        var clickedUser by remember { mutableStateOf(User()) }
        var isUserClickedDialogOpen by remember { mutableStateOf(false) }
        var isRemoveMemberDialogOpen by rememberSaveable { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val clickedUserOptions = mutableListOf<OptionItem>().apply {
            if (currentUserId != clickedUser.id) {
                add(
                    OptionItem(
                        label = "Message ${clickedUser.name.trim().substringBefore(" ")}",
                        onClick = {
                            onAction(GroupDetailsAction.OnMessageUser(clickedUser))
                            isUserClickedDialogOpen = false
                        }
                    )
                )
            }
            add(
                OptionItem(
                    label = "View ${clickedUser.name.trim().substringBefore(" ")}'s profile",
                    onClick = {
                        onAction(GroupDetailsAction.OnViewUserProfile(clickedUser.id))
                        isUserClickedDialogOpen = false
                    }
                )
            )
            if (isAdmin && clickedUser.id != currentUserId) {
                add(
                    OptionItem(
                        label = "Remove ${clickedUser.name.trim().substringBefore(" ")} from group",
                        onClick = {
                            isRemoveMemberDialogOpen = true
                            isUserClickedDialogOpen = false
                        }
                    )
                )
            }
        }
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Group Details",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(GroupDetailsAction.OnBackClicked)
                        }) {
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
                color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.2f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    ModifiableAvatarSection(
                        avatarURL = avatarURL,
                        isModifiable = isAdmin,
                        onImagePicked = { array, extension ->
                            onAction(
                                GroupDetailsAction.OnChangeAvatar(
                                    array,
                                    extension
                                )
                            )
                        }
                    )
                    GroupDetailsNameSection(
                        name = name,
                        isModifiable = isAdmin,
                        onChangeName = { newName ->
                            onAction(GroupDetailsAction.OnChangeName(newName))
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)
                    GroupMembersSection(
                        modifier = Modifier.padding(16.dp),
                        members = members,
                        admins = admins,
                        isAdmin = isAdmin,
                        onAddMembersClicked = { onAction(GroupDetailsAction.OnAddMembersClicked) },
                        onUserClicked = {
                            clickedUser = it
                            isUserClickedDialogOpen = true
                        }
                    )
                }
                if (isRemoveMemberDialogOpen) {
                    ConfirmActionAlertDialog(
                        onDismissRequest = {
                            isRemoveMemberDialogOpen = false
                        },
                        onConfirmation = {
                            onAction(GroupDetailsAction.OnRemoveMember(clickedUser.id))
                            isRemoveMemberDialogOpen = false
                        },
                        dialogTitle = stringResource(resource = Res.string.confirm_member_removal)
                    )
                }
                if (isUserClickedDialogOpen) {
                    UserClickedDialog(
                        options = clickedUserOptions,
                        onDismiss = { isUserClickedDialogOpen = false },
                        sheetState = sheetState,
                        windowWidthSizeClass = windowWidthSizeClass
                    )
                }
            }
        }
    }
}
