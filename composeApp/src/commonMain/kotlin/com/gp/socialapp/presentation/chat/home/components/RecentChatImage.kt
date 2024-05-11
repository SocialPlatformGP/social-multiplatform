package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun RecentChatImage(currentUserId: String, recentRoom: RecentRoom) {
    if (recentRoom.isPrivate) {
        val imageUrl =
            if (recentRoom.senderId == currentUserId) recentRoom.receiverPicUrl else recentRoom.senderPicUrl
        CircularAvatar(
            imageURL = imageUrl,
            size = 64.dp,
            placeHolderImageVector = Icons.Default.Person,
        )
    } else {
        CircularAvatar(
            imageURL = recentRoom.senderPicUrl,
            size = 64.dp,
            placeHolderImageVector = Icons.Default.Groups,
        )
    }

}