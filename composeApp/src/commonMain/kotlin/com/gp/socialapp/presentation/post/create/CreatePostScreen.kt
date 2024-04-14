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
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.create_post

data class CreatePostScreen(val openedFeedTab: FeedTab) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreatePostScreenModel>()
        val state by screenModel.uiState.collectAsState()
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
                onPostClick = { title, body -> screenModel.onCreatePost(title, body, openedFeedTab.title) },
                confirmNewTags = {
                    screenModel.insertNewTags(it)
                },
                onAddImage = { file ->
                    screenModel.onAddFile(file)
                },
                onAddTags = screenModel::onAddTags,
                onRemoveTags = screenModel::onRemoveTags
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
        onAddImage: (PostAttachment) -> Unit
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
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { files ->
                scope.launch {
                    files.firstOrNull()?.let { file ->
                        val image = file.readByteArray(context)
                        onAddImage(
                            PostAttachment(
                                file = image,
                                name = file.getName(context) ?: "",
                                type = FilePickerFileType.ImageContentType,
                                size = image.size.toLong()
                            )
                        )
                    }
                }
            }
        )
        Scaffold(
            topBar = {
                CreatePostTopBar(
                    onBackClick = onBackClick,
                    onPostClick = {onPostClick(title, body)},
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
                        title =newTitle
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
                    onTagClick = {tag ->
                        onRemoveTags(setOf(tag))
                    }
                )
                FilesRow(
                    state.files,
                    onFileClick = { file ->
                        println("File clicked: $file")
                    }
                )
                HorizontalDivider()
                BottomOptionRow(
                    onAddFileClicked = { /**/ },
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
                    onAddVideoClicked = {/*TODO: Add video picker*/ },
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

}

