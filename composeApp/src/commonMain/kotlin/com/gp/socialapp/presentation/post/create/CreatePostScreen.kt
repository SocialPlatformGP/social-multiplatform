package com.gp.socialapp.presentation.post.create

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<CreatePostViewModel>()
        MaterialTheme {
            CreatePostContent(screenModel = screenModel)
        }
    }

    @Composable
    private fun CreatePostContent(
        screenModel: CreatePostViewModel
    ) {
        Scaffold(
            topBar = { CreatePostTopBar() },
        ) {

        }
    }

}



