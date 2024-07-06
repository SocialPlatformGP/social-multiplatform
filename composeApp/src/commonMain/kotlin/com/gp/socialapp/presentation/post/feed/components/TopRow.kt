package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.UserCircle

@Composable
fun TopRow(
    imageUrl: String,
    userName: String,
    publishedAt: String,
    isAuthor: Boolean,
    onEditPostClicked: () -> Unit,
    onDeletePostClicked: () -> Unit,
    onUserClick: () -> Unit
) {
    Row(
        modifier = Modifier.background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularAvatar(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            imageURL = imageUrl,
            size = 36.dp,
            placeHolderImageVector = FontAwesomeIcons.Solid.UserCircle,
            onClick = onUserClick
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserName(
                userName = userName,
                onClick = onUserClick,
            )
            PostDate(
                publishedAt = publishedAt,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        OptionButton(
            onEditPostClicked = onEditPostClicked,
            onDeletePostClicked = onDeletePostClicked,
            isAuthor = isAuthor
        )
    }
}