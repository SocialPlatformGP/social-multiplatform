package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
fun MessageImageAttachment(
    imageURL: String,
    onImageClicked: () -> Unit,
    maxHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = Color.LightGray
    ) {
        AutoSizeBox(
            url = imageURL,
        ) { action ->
            when (action) {
                is ImageAction.Success -> {
                    Image(
                        painter = rememberImageSuccessPainter(
                            action = action,
                            filterQuality = FilterQuality.Medium
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .heightIn(max = maxHeight)
                    )
                }

                is ImageAction.Failure -> {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp),
                    )
                }

                is ImageAction.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp),
                    )
                }
            }
        }
    }
}