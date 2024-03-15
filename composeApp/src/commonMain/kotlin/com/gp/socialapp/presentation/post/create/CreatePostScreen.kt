package com.gp.socialapp.presentation.post.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.theme.tag_color_1
import com.gp.socialapp.theme.tag_color_2
import com.gp.socialapp.theme.tag_color_3
import com.gp.socialapp.theme.tag_color_4
import com.gp.socialapp.theme.tag_color_5
import com.gp.socialapp.theme.tag_color_6
import com.gp.socialapp.theme.tag_color_7
import com.gp.socialapp.theme.tag_color_8
import com.gp.socialapp.theme.tag_color_9
import kotlinx.coroutines.launch

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<CreatePostScreenModel>()
        val state by screenModel.uiState.collectAsState()
        MaterialTheme {
            CreatePostContent(
                state = state,
                channelTags = screenModel.channelTags.value,
                onBackClick = { navigator.pop() },
                onPostClick = { screenModel.onCreatePost() },
                onTitleChange = { screenModel.onTitleChange(it) },
                onBodyChange = { screenModel.onBodyChange(it) },
                confirmNewTags = { screenModel.insertNewTags(it) }
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
        confirmNewTags: (List<Tag>) -> Unit,
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        var skipPartiallyExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
        var existingTagsDialogState by remember { mutableStateOf(false) }
        var newTagDialogState by remember { mutableStateOf(false) }
        Scaffold {
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
                //TODO: Add tags row
                TagsRow()
                //TODO: Add files row
                FilesRow()
                HorizontalDivider()
                BottomOptionRow(
                    onAddFileClicked = {/* TODO: Add file picker*/ },
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

                ModalBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    sheetState = bottomSheetState,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = { newTagDialogState = true },
                        ) {
                            Text(
                                text = "Add New Tag",
                            )
                        }
                        Button(
                            onClick = { existingTagsDialogState = true },
                        ) {
                            Text(
                                text = "Select Old Tag",
                            )
                        }
                    }


                }
            }
            if (existingTagsDialogState) {
                var tempTags by remember { mutableStateOf(emptySet<Tag>()) }
                AlertDialog(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    onDismissRequest = { existingTagsDialogState = false },
                    title = { Text(text = "Select Tag") },
                    text = {
                        FlowRow {
                            channelTags.toSet().forEach { tag ->
                                AssistChip(
                                    onClick = {
                                        tempTags = if (tempTags.contains(tag)) {
                                            tempTags - tag
                                        } else {
                                            tempTags + tag
                                        }
                                    },
                                    label = { Text(text = tag.label) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(tag.intColor)
                                    ),
                                    leadingIcon = {
                                        if (tempTags.contains(tag)) {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null
                                            )
                                        }
                                    })
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            confirmNewTags(tempTags.toList())
                            existingTagsDialogState = false
                        }) {
                            Text(text = "Add")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { existingTagsDialogState = false }) {
                            Text(text = "Cancel")
                        }
                    })
            }
            if (newTagDialogState) {
                var tempTag by remember { mutableStateOf(Tag("", 0)) }
                val keyboardController = LocalSoftwareKeyboardController.current
                val fixedColor = listOf(
                    tag_color_1,
                    tag_color_2,
                    tag_color_3,
                    tag_color_4,
                    tag_color_5,
                    tag_color_6,
                    tag_color_7,
                    tag_color_8,
                    tag_color_9,
                ).shuffled()

                AlertDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onDismissRequest = { newTagDialogState = false },
                    title = { Text(text = "Add new Tag") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = tempTag.label,
                                onValueChange = {
                                    tempTag = tempTag.copy(label = it)
                                },
                                label = { Text(text = "Tag") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = {
                                    keyboardController?.hide()
                                }),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Label,
                                        contentDescription = null,
                                        tint = Color(tempTag.intColor)
                                    )
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow {
                                fixedColor.forEach { color ->
                                    AssistChip(
                                        onClick = {
                                            tempTag = tempTag.copy(intColor = color.toArgb())
                                        },
                                        label = { Text(text = "") },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = color
                                        ),
                                        leadingIcon = {
                                            if (tempTag.intColor == color.toArgb()) {
                                                Icon(
                                                    imageVector = Icons.Filled.Check,
                                                    contentDescription = null
                                                )
                                            }
                                        })
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            confirmNewTags(listOf(tempTag))
                            newTagDialogState = false
                        }) {
                            Text(text = "Add")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { newTagDialogState = false }) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
        }
    }
}
