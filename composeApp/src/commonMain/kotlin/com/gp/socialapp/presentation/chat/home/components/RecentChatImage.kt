package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.RecentRoomResponse
import com.seiko.imageloader.ui.AutoSizeImage

@Composable
fun RecentChatImage(recentRoom: RecentRoomResponse) {
    if (recentRoom.pic_url.isEmpty() && recentRoom.isPrivate) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "room image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    } else if (recentRoom.pic_url.isEmpty() && !recentRoom.isPrivate) {
        Image(
            imageVector = Icons.Default.Groups,
            contentDescription = "room image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    } else {
        AutoSizeImage(
            url = recentRoom.pic_url,
            contentDescription = "room image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    }

}