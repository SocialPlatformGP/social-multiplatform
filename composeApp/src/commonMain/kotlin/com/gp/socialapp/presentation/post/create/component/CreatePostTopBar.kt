package com.gp.socialapp.presentation.post.create.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostTopBar(
    onBackClick: () -> Unit,
    onPostClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(resource = Res.string.create_post),
            )
        },
        navigationIcon = {
            MyIconButton(
                onClick = onBackClick,
                icon = Icons.Filled.ArrowBackIosNew,
            )
        },
        actions = {
            MyIconButton(
                onClick = onPostClick,
                icon = Icons.Filled.Check,
            )
        }
    )
}