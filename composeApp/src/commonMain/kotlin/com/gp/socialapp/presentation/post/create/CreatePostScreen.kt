package com.gp.socialapp.presentation.post.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyBottomSheet
import com.gp.socialapp.presentation.post.create.component.MyExistingTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.presentation.post.feed.FeedTab
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.create_post

data class CreatePostScreen(val openedFeedTab: FeedTab, val communityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreatePostScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.init(openedFeedTab, communityId)
            }
        )
        val existingTags by screenModel.channelTags.collectAsState()
        if (state.createdState) {
            navigator.pop()
            screenModel.resetUiState()
        }
        MaterialTheme {
            CreatePostContent(
                state = state,
                channelTags = existingTags,
                onBackClick = { navigator.pop() },
                onPostClick = { title, body ->
                    screenModel.onCreatePost(
                        title = title,
                        body = body,
                    )
                },
                confirmNewTags = {
                    screenModel.insertNewTags(it)
                },
                onAddTags = screenModel::onAddTags,
                onRemoveTags = screenModel::onRemoveTags,
                onAddFile = screenModel::onAddFile,
                onRemoveFile = screenModel::onRemoveFile
            )
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreatePostContent(
        state: CreatePostUIState,
        channelTags: List<Tag>,
        onBackClick: () -> Unit,
        onPostClick: (String, String) -> Unit,
        onAddTags: (Set<Tag>) -> Unit,
        onRemoveTags: (Set<Tag>) -> Unit,
        confirmNewTags: (Set<Tag>) -> Unit,
        onAddFile: (PostAttachment) -> Unit,
        onRemoveFile: (PostAttachment) -> Unit
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        val skipPartiallyExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
        var existingTagsDialogState by remember { mutableStateOf(false) }
        var newTagDialogState by remember { mutableStateOf(false) }
        var selectedTags: List<Tag> by remember { mutableStateOf(emptyList()) }
        var title by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }
        val context = LocalPlatformContext.current
        val imagePicker = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
            selectionMode = FilePickerSelectionMode.Multiple,
            onResult = { files ->
                uploadPostFiles(
                    scope,
                    files,
                    context,
                    onAddFile,
                    FilePickerFileType.ImageContentType
                )
            }
        )
        val videoPicker = rememberFilePickerLauncher(
            type = FilePickerFileType.Video,
            selectionMode = FilePickerSelectionMode.Multiple,
            onResult = { files ->
                uploadPostFiles(
                    scope,
                    files,
                    context,
                    onAddFile,
                    FilePickerFileType.VideoContentType
                )
            }
        )
        val filePicker = rememberFilePickerLauncher(
            type = FilePickerFileType.All,
            selectionMode = FilePickerSelectionMode.Multiple,
            onResult = { files ->
                uploadPostFiles(
                    scope,
                    files,
                    context,
                    onAddFile,
                    FilePickerFileType.AllContentType
                )
            }
        )
        Scaffold(
            topBar = {
                CreatePostTopBar(
                    onBackClick = onBackClick,
                    onPostClick = { onPostClick(title, body) },
                    stringResource(Res.string.create_post)
                )
            }
        ) { it ->
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                MyTextField(
                    value = title,
                    label = "Title",
                    onValueChange = { newTitle ->
                        title = newTitle
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                )
                MyTextField(
                    value = body,
                    label = "Body",
                    onValueChange = { newBody ->
                        body = newBody
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                TagsRow(
                    tags = state.tags,
                    onTagClick = { tag ->
                        onRemoveTags(setOf(tag))
                    }
                )
                FilesRow(
                    modifier = Modifier.fillMaxWidth(),
                    state.files,
                    onFileDelete = { file ->
                        onRemoveFile(file)
                    }
                )
                HorizontalDivider()
                BottomOptionRow(
                    modifier = Modifier.fillMaxWidth(),
                    onAddFileClicked = {
                        filePicker.launch()
                    },
                    onAddImageClicked = {
                        imagePicker.launch()
                    },
                    onAddTagClicked = {
                        scope.launch { bottomSheetState.show() }.invokeOnCompletion {
                            if (bottomSheetState.isVisible) {
                                openBottomSheet = true
                            }
                        }
                    },
                    onAddVideoClicked = {
                        videoPicker.launch()
                    },
                    pickedFileType = state.files.firstOrNull()?.type ?: ""
                )
            }
            if (openBottomSheet) {
                MyBottomSheet(
                    openBottomSheet = { value ->
                        openBottomSheet = value
                    },
                    bottomSheetState = bottomSheetState,
                    existingTagsDialogState = { value ->
                        existingTagsDialogState = value
                    },
                    newTagDialogState = { value ->
                        newTagDialogState = value
                    },
                )
            }
            if (existingTagsDialogState) {
                MyExistingTagAlertDialog(
                    existingTagsDialogState = { value ->
                        existingTagsDialogState = value
                    },
                    channelTags = channelTags,
                    selectedTags = onAddTags,
                )
            }
            if (newTagDialogState) {
                NewTagAlertDialog(
                    newTagDialogState = { value ->
                        newTagDialogState = value
                    },
                    confirmNewTags = confirmNewTags,
                )

            }
        }
    }

    private fun uploadPostFiles(
        scope: CoroutineScope,
        files: List<KmpFile>,
        context: PlatformContext,
        onAddFile: (PostAttachment) -> Unit,
        type: String
    ) {
        scope.launch {
            files.forEach { file ->
                val image = file.readByteArray(context)
                onAddFile(
                    PostAttachment(
                        file = image,
                        name = file.getName(context) ?: "",
                        type = type,
                        size = image.size.toLong()
                    )
                )
            }
        }
    }

}

