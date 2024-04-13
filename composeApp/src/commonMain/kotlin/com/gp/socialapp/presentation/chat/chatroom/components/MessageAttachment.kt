package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eygraber.uri.Uri

@Composable
fun MessageAttachment(
    modifier: Modifier = Modifier,
    fileURI: Uri,
    fileType: String,
    fileName: String,
    maxHeight: Dp,
    onFileClicked: () -> Unit = {},
    onImageClicked: () -> Unit = {},
) {
    Surface(
        shape = RoundedCornerShape(20.dp), color = Color.Transparent
    ) {
        when {
            fileType.contains("image") -> {
                MessageImageAttachment(
                    imageURL = fileURI.toString(),
                    onImageClicked = { onImageClicked() },
                    maxHeight = maxHeight,
                    modifier = modifier,
                )
            }

            else -> {
                MessageFileAttachment(
                    fileType = fileType,
                    fileName = fileName,
                    onFileClicked = { onFileClicked() },
                    modifier = modifier,
                )
            }
        }
    }
}