package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun RowScope.MessageUserAvatar(
    isCurrentUser: Boolean,
    isSameSender: Boolean,
    isPrivateChat: Boolean,
    imageURL: String,
    onUserClicked: () -> Unit,
) {
    if (!isCurrentUser && !isSameSender && !isPrivateChat) {
        CircularAvatar(
            imageURL = imageURL,
            size = 32.dp,
            onClick = { onUserClicked() },
            placeHolderImageVector = Icons.Filled.AccountCircle,
            modifier = Modifier.align(Alignment.Top).padding(horizontal = 8.dp)
        )
    } else if (!isCurrentUser && isSameSender && !isPrivateChat) {
        Spacer(modifier = Modifier.width(48.dp))
    } else if (isPrivateChat && !isCurrentUser) {
        Spacer(modifier = Modifier.width(8.dp))
    }
}