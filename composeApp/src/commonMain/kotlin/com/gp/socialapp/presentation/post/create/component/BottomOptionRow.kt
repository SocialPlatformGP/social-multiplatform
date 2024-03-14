package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomOptionRow(
    onAddTagClicked: () -> Unit,
    onAddImageClicked: () -> Unit,
    onAddVideoClicked: () -> Unit,
    onAddFileClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MyOutlinedTextButton(
            onClick = onAddTagClicked,
            label = "add tags"
        )
        MyIconButton(
            onClick = onAddImageClicked,
            icon = Icons.Filled.Image,
        )
        MyIconButton(
            onClick = onAddVideoClicked,
            icon = Icons.Filled.VideoFile,
        )
        MyIconButton(
            onClick = onAddFileClicked,
            icon = Icons.Filled.AttachFile,
        )

    }

}



