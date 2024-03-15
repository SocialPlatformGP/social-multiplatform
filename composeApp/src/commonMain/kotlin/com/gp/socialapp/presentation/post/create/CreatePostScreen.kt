package com.gp.socialapp.presentation.post.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyBottomSheet
import com.gp.socialapp.presentation.post.create.component.MyExistingTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import kotlinx.coroutines.launch

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<CreatePostScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val existingTags by screenModel.channelTags.collectAsState()
        MaterialTheme {
            CreatePostContent(
                state = state,
                channelTags = existingTags,
                onBackClick = { navigator.pop() },
                onPostClick = { screenModel.onCreatePost() },
                onTitleChange = { screenModel.onTitleChange(it) },
                onBodyChange = { screenModel.onBodyChange(it) },
                confirmNewTags = {
                    screenModel.insertNewTags(it)
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    private fun CreatePostContent(
        state: CreatePostUIState,
        channelTags: List<Tag>,
        onBackClick: () -> Unit,
        onPostClick: () -> Unit,
        onTitleChange: (String) -> Unit,
        onBodyChange: (String) -> Unit,
        confirmNewTags: (Set<Tag>) -> Unit,
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        var skipPartiallyExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
        var existingTagsDialogState by remember { mutableStateOf(false) }
        var newTagDialogState by remember { mutableStateOf(false) }
        var selectedTags: List<Tag> by remember { mutableStateOf(emptyList()) }

        Scaffold(
            topBar = {
                CreatePostTopBar(
                    onBackClick = onBackClick,
                    onPostClick = onPostClick
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {

                MyTextField(
                    value = state.title,
                    label = "Title",
                    onValueChange = { onTitleChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                )
                MyTextField(
                    value = state.body,
                    label = "Body",
                    onValueChange = { onBodyChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                TagsRow(
                    tags = selectedTags.toSet().toList(),
                    onTagClick = { tag ->
                        println("Tag clicked: $tag")
                        println("Selected tags: $selectedTags")
                        selectedTags -= tag
                    }
                )
                //TODO: Add files row
                FilesRow()
                HorizontalDivider()
                BottomOptionRow(
                    onAddFileClicked = { openFilePicker() },
                    onAddImageClicked = {/*TODO: Add image picker*/ },
                    onAddTagClicked = {
                        scope.launch { bottomSheetState.show() }.invokeOnCompletion {
                            if (bottomSheetState.isVisible) {
                                openBottomSheet = true
                            }
                        }
                    },
                    onAddVideoClicked = {/*TODO: Add video picker*/ }
                )
            }
            if (openBottomSheet) {
                MyBottomSheet(
                    openBottomSheet = {
                        openBottomSheet = it
                    },
                    bottomSheetState = bottomSheetState,
                    existingTagsDialogState = {
                        existingTagsDialogState = it
                    },
                    newTagDialogState = {
                        newTagDialogState = it
                    },
                )
            }
            if (existingTagsDialogState) {
                MyExistingTagAlertDialog(
                    existingTagsDialogState = {
                        existingTagsDialogState = it
                    },
                    channelTags = channelTags,
                    selectedTags = {
                        selectedTags += it
                    },
                    confirmNewTags = {
                        confirmNewTags(it)
                    }

                )
            }
            if (newTagDialogState) {
                NewTagAlertDialog(
                    newTagDialogState = {
                        newTagDialogState = it
                    },
                    confirmNewTags = {
                        confirmNewTags(it)
                    },
                    selectedTags = {
                        selectedTags += it
                    }
                )

            }
        }
    }

}

expect fun openFilePicker()
