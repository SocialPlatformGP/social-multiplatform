package com.gp.socialapp.presentation.post.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.TagsRow

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<CreatePostViewModel>()
        MaterialTheme {
            CreatePostContent(screenModel = screenModel)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CreatePostContent(
        screenModel: CreatePostViewModel
    ) {
        val state by screenModel.uiState.collectAsState()
        Scaffold(
            topBar = { CreatePostTopBar() },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                MyTextField(
                    value = state.title,
                    label = "Title",
                    onValueChange = { screenModel.onTitleChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                )
                MyTextField(
                    value = state.body,
                    label = "Body",
                    onValueChange = { screenModel.onBodyChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                //TODO: Add tags row
                TagsRow()
                //TODO: Add files row
                FilesRow()
                HorizontalDivider()
                BottomOptionRow()
            }

        }
    }


}



