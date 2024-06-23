package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.material.utils.MimeType

@Composable
fun MessageAttachment(
    modifier: Modifier = Modifier,
    fileUrl: String,
    fileName: String,
    fileSize: Long,
    maxHeight: Dp,
    onFileClicked: () -> Unit = {},
    onImageClicked: () -> Unit = {},
) {
    Surface(
        shape = RoundedCornerShape(8.dp), color = Color.Transparent
    ) {
        when {
            MimeType.getMimeTypeFromFileName(fileName) is MimeType.Image -> {
                MessageImageAttachment(
                    imageURL = fileUrl,
                    onImageClicked = { onImageClicked() },
                    maxHeight = maxHeight,
                    modifier = modifier,
                )
            }

            else -> {
                MessageFileAttachment(
                    fileName = fileName,
                    onFileClicked = { onFileClicked() },
                    modifier = modifier,
                    size = fileSize,
                )
            }
        }
    }
}