package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
fun UserImage(
    imageLink: String, size: Dp = 36.dp, onClick: () -> Unit
) {
    val imageModifier = Modifier.padding(start = 8.dp, end = 8.dp).size(size).clip(CircleShape)
        .clickable { onClick() }
    AutoSizeBox(imageLink) { action ->
        when (action) {
            is ImageAction.Success -> {
                Image(
                    rememberImageSuccessPainter(action),
                    contentDescription = "User Avatar",
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = imageModifier.padding(start = 8.dp, end = 8.dp).size(size)
                        .clip(CircleShape)
                )
            }
        }
    }
}