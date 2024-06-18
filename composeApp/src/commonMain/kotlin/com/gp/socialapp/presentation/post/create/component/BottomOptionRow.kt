package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomOptionRow(
    modifier: Modifier = Modifier,
    onAddTagClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MyOutlinedTextButton(
            onClick = {
                onAddTagClicked()
            },
            label = "Tags"
        )
    }

}



