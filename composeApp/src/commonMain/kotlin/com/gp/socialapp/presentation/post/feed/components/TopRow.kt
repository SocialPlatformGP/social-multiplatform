package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
        UserImage(imageLink = imageUrl, onClick = onUserClick)
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