package com.gp.socialapp.presentation.chat.private_chat.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.seiko.imageloader.ui.AutoSizeImage

@Composable
fun UserImage(user: User) {
    AutoSizeImage(
        url = user.profilePictureURL,
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}