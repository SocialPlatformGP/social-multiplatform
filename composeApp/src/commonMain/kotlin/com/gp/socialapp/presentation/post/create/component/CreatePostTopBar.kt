package com.gp.socialapp.presentation.post.create.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(resource = Res.string.create_post)
            )
        },
        navigationIcon = {
            Icons.Filled.ArrowBackIosNew
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icons.Filled.Check
            }
        }

    )
}