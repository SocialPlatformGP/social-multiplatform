package com.gp.socialapp.presentation.post.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.TagsRow
import kotlinx.coroutines.launch

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<CreatePostViewModel>()
        val state by screenModel.uiState.collectAsState()
        MaterialTheme {
            CreatePostContent(
                state = state,
                onBackClick = { navigator.pop() },
                onPostClick = { screenModel.onCreatePost() },
                onTitleChange = { screenModel.onTitleChange(it) },
                onBodyChange = { screenModel.onBodyChange(it) }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreatePostContent(
        state: CreatePostUIState,
        onBackClick: () -> Unit,
        onPostClick: () -> Unit,
        onTitleChange: (String) -> Unit,
        onBodyChange: (String) -> Unit
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        var skipPartiallyExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
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
                    onAddFileClicked = {
                        scope.launch { bottomSheetState.show() }.invokeOnCompletion {
                            if (bottomSheetState.isVisible) {
                                openBottomSheet = true
                            }
                        }
                    },
                    onAddImageClicked = {/*TODO: Add image picker*/ },
                    onAddTagClicked = {/*TODO: Add tag picker*/ },
                    onAddVideoClicked = {/*TODO: Add video picker*/ }
                )
            }
            if (openBottomSheet) {

                ModalBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    sheetState = bottomSheetState,
//                    windowInsets = windowInsets,
                ) {
                    LazyColumn {
                        items(50) {
                            ListItem(
                                headlineContent = { Text("Item $it") },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = "Localized description"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }


    }


}
