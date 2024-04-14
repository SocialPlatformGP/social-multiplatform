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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.presentation.chat.chatroom.components.ChatRoomTopBar
import com.gp.socialapp.presentation.chat.chatroom.components.DropDownItem
import com.gp.socialapp.presentation.chat.chatroom.components.EditMessageDialog
import com.gp.socialapp.presentation.chat.chatroom.components.ImagePreviewDialog
import com.gp.socialapp.presentation.chat.chatroom.components.MessageInput
import com.gp.socialapp.presentation.chat.chatroom.components.MessagesContent
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ChatRoomScreen(
    private val roomId: String,
    private val roomAvatarUrl: String,
    private val roomTitle: String,
    private val isPrivate: Boolean
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<ChatRoomScreenModel>()
        val state by screenModel.uiState.collectAsState()
        var isInitialized by remember { mutableStateOf(false) }
        if (!isInitialized) {
            screenModel.initScreen(roomId, isPrivate)
            isInitialized = true
        }
        ChatRoomContent(
            messages = state.messages,
            currentUserId = state.currentUserId,
            onAction = { action ->
                when(action) {
                    is ChatRoomAction.OnBackPressed -> navigator.pop()
                    is ChatRoomAction.OnChatHeaderClicked -> {
                        TODO("Navigate to group details or user profile")
                    }
                    is ChatRoomAction.OnUserClicked -> {
                        TODO("Navigate to user profile")
                    }
                    else -> {
                        screenModel.handleUiAction(action)
                    }
                }
            }
        )
    }

    @Composable
    private fun ChatRoomContent(
        modifier: Modifier = Modifier,
        messages: List<Message>,
        currentUserId: String,
        onAction: (ChatRoomAction) -> Unit,
        scope: CoroutineScope = rememberCoroutineScope(),
    ) {
        val scrollState = rememberLazyListState()
        val context = LocalPlatformContext.current
        var isImagePreviewDialogOpen by rememberSaveable { mutableStateOf(false) }
        var previewedImageURL by rememberSaveable { mutableStateOf("") }
        var isEditMessageDialogOpen by rememberSaveable { mutableStateOf(false) }
        var editedMessageID by remember { mutableStateOf("") }
        var editedMessageBody by remember { mutableStateOf("") }
        var pickedFileType: FilePickerFileType by remember { mutableStateOf(FilePickerFileType.All) }
        val filePicker = rememberFilePickerLauncher(
            type = pickedFileType,
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { files ->
                scope.launch {
                    files.firstOrNull()?.let { file ->
                        val bytearray = file.readByteArray(context)
                        val name = file.getName(context) ?: ""
                        val type = when (pickedFileType) {
                            FilePickerFileType.Image -> FilePickerFileType.ImageContentType
                            FilePickerFileType.Video -> FilePickerFileType.VideoContentType
                            FilePickerFileType.Audio -> FilePickerFileType.AudioContentType
                            FilePickerFileType.Pdf -> FilePickerFileType.PdfContentType
                            FilePickerFileType.Word -> FilePickerFileType.WordDocumentContentType
                            FilePickerFileType.Spreadsheet -> FilePickerFileType.ExcelSpreadsheetContentType
                            FilePickerFileType.Presentation -> FilePickerFileType.PowerPointPresentationContentType
                            FilePickerFileType.Text -> FilePickerFileType.TextContentType
                            else -> FilePickerFileType.AllContentType
                        }
                        onAction(ChatRoomAction.OnAttachmentPicked(bytearray, name, type))
                        pickedFileType = FilePickerFileType.All
                    }
                }
            })
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            topBar = {
                ChatRoomTopBar(
                    onBackPressed = { onAction(ChatRoomAction.OnBackPressed) },
                    onChatHeaderClicked = {
                        onAction(
                            ChatRoomAction.OnChatHeaderClicked(
                                roomId,
                                isPrivate
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
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MessagesContent(
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
                                    }
                                }

                                is ChatRoomAction.OnAttachClicked -> {
                                    pickedFileType = action.type
                                    filePicker.launch()
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
                        onAction = onAction,
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
                        ImagePreviewDialog(imageURL = previewedImageURL,
                            onDismissRequest = { isImagePreviewDialogOpen = false })
                    }
                }
            }
        }
    }
}
