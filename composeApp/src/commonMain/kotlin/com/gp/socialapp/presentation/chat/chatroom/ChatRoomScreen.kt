package com.gp.socialapp.presentation.chat.chatroom

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.presentation.chat.chatroom.components.ChatRoomTopBar
import com.gp.socialapp.presentation.chat.chatroom.components.DropDownItem
import com.gp.socialapp.presentation.chat.chatroom.components.EditMessageDialog
import com.gp.socialapp.presentation.chat.chatroom.components.ImagePreviewDialog
import com.gp.socialapp.presentation.chat.chatroom.components.MessageInput
import com.gp.socialapp.presentation.chat.chatroom.components.MessagesContent
import com.gp.socialapp.presentation.chat.groupdetails.GroupDetailsScreen
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.userprofile.UserProfileScreen
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ChatRoomScreen(
    private val roomId: Long,
    private val roomAvatarUrl: String,
    private val roomTitle: String,
    private val isPrivate: Boolean
) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<ChatRoomScreenModel>()
        val windowSizeClass = calculateWindowSizeClass()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(onStarted = {
            screenModel.initScreen(roomId, isPrivate)
        }, onDisposed = {
            screenModel.onDispose()
        })
        ChatRoomContent(messages = state.messages,
            currentUserId = state.currentUser.id,
            attachment = state.currentAttachment,
            onAction = { action ->
                when (action) {
                    is ChatRoomAction.OnBackPressed -> {
                        navigator.pop()
                    }

                    is ChatRoomAction.OnChatHeaderClicked -> {
                        if (isPrivate){
                            val userId = state.currentRoom.members.keys.first { it != state.currentUser.id }
                            navigator.push(UserProfileScreen(userId))
                        } else {
                            navigator.push(
                                GroupDetailsScreen(
                                    roomId = action.roomId,
                                    roomTitle = roomTitle,
                                    roomAvatarUrl = roomAvatarUrl,
                                )
                            )
                        }
                    }

                    is ChatRoomAction.OnUserClicked -> {
                        navigator.push(UserProfileScreen(action.userId))
                    }

                    else -> {
                        screenModel.handleUiAction(action)
                    }
                }
            },
            windowWidthSizeClass = windowSizeClass.widthSizeClass)
    }

    @Composable
    private fun ChatRoomContent(
        modifier: Modifier = Modifier,
        messages: List<Message>,
        currentUserId: String,
        isMessagesLoading: Boolean = false,
        isSendLoading: Boolean = false,
        windowWidthSizeClass: WindowWidthSizeClass,
        attachment: MessageAttachment,
        onAction: (ChatRoomAction) -> Unit,
        scope: CoroutineScope = rememberCoroutineScope(),
    ) {
        val scrollState = rememberLazyListState()
        val context = LocalPlatformContext.current
        var isImagePreviewDialogOpen by rememberSaveable { mutableStateOf(false) }
        var previewedImageURL by rememberSaveable { mutableStateOf("") }
        var isEditMessageDialogOpen by rememberSaveable { mutableStateOf(false) }
        var editedMessageID by remember { mutableStateOf(0L) }
        var editedMessageBody by remember { mutableStateOf("") }
        val allFilePicker = rememberFilePickerLauncher(
            type = FilePickerFileType.All,
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { files ->
                scope.launch {
                    files.firstOrNull()?.let { file ->
                        val bytearray = file.readByteArray(context)
                        val name = file.getName(context) ?: ""
                        val type = MimeType.getMimeTypeFromFileName(name)
                        val extension = MimeType.getExtensionFromMimeType(type)
                        onAction(ChatRoomAction.OnAttachmentPicked(bytearray, name, extension))
                    }
                }
            })
        val imageFilePicker = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { files ->
                scope.launch {
                    files.firstOrNull()?.let { file ->
                        val bytearray = file.readByteArray(context)
                        val name = file.getName(context) ?: ""
                        val type = MimeType.getMimeTypeFromFileName(name)
                        val extension = MimeType.getExtensionFromMimeType(type)
                        onAction(ChatRoomAction.OnAttachmentPicked(bytearray, name, extension))
                    }
                }
            }
        )
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            topBar = {
                ChatRoomTopBar(onBackPressed = { onAction(ChatRoomAction.OnBackPressed) },
                    onChatHeaderClicked = {
                        onAction(
                            ChatRoomAction.OnChatHeaderClicked(
                                roomId, isPrivate
                            )
                        )
                    },
                    isPrivateChat = isPrivate,
                    chatImageURL = roomAvatarUrl,
                    chatTitle = roomTitle
                )
            },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
                .exclude(WindowInsets.ime),
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                val boxWithConstraintsScope = this
                if(isMessagesLoading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.TopCenter).padding(top = if(windowWidthSizeClass != WindowWidthSizeClass.Compact) 48.dp else 24.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MessagesContent(
                        windowWidthSizeClass = windowWidthSizeClass,
                        messages = messages,
                        currentUserId = currentUserId,
                        isPrivateChat = isPrivate,
                        onAction = { action ->
                            when (action) {
                                is ChatRoomAction.OnImageClicked -> {
                                    previewedImageURL = action.imageUrl
                                    isImagePreviewDialogOpen = true
                                }

                                is ChatRoomAction.OnDropDownItemClicked -> {
                                    when (action.action) {
                                        DropDownItem.EDIT -> {
                                            editedMessageID = action.messageId
                                            editedMessageBody = action.messageContent
                                            isEditMessageDialogOpen = true
                                        }

                                        DropDownItem.DELETE -> {
                                            onAction(ChatRoomAction.OnDeleteMessage(action.messageId))
                                        }

                                        DropDownItem.REPORT -> {
                                            onAction(ChatRoomAction.OnReportMessage(action.messageId))
                                        }
                                    }
                                }

                                is ChatRoomAction.OnAttachClicked -> {
                                    when(action.type){
                                        FilePickerFileType.Image -> imageFilePicker.launch()
                                        else -> allFilePicker.launch()
                                    }
                                }

                                else -> onAction(action)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        scrollState = scrollState,
                        dropDownItems = DropDownItem.entries,
                        maxScreenWidthDP = boxWithConstraintsScope.maxWidth,
                        maxScreenHeightDP = boxWithConstraintsScope.maxHeight,
                    )
                    MessageInput(
                        onAction = { action ->
                            when (action) {
                                is ChatRoomAction.OnAttachClicked -> {
                                    println("Attach clicked")
                                    when(action.type){
                                        FilePickerFileType.Image -> imageFilePicker.launch()
                                        else -> allFilePicker.launch()
                                    }
                                }
                                else -> onAction(action)
                            }
                        },
                        attachment = attachment,
                        isLoading = isSendLoading,
                    )
                    if (isEditMessageDialogOpen) {
                        EditMessageDialog(initialMessage = editedMessageBody,
                            onConfirmation = { editedMessage ->
                                onAction(
                                    ChatRoomAction.OnUpdateMessage(
                                        editedMessageID, editedMessage
                                    )
                                )
                                isEditMessageDialogOpen = false
                            },
                            onDismissRequest = { isEditMessageDialogOpen = false })
                    }
                    if (isImagePreviewDialogOpen) {
                        ImagePreviewDialog(
                            imageURL = previewedImageURL,
                            maxWidth = boxWithConstraintsScope.maxWidth,
                            onDismissRequest = { isImagePreviewDialogOpen = false }
                        )
                    }
                }
            }
        }
    }
}
