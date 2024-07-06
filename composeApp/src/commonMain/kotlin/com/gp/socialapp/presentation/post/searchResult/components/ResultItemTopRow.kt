package com.gp.socialapp.presentation.post.searchResult.components

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
import com.gp.socialapp.presentation.post.feed.components.PostDate
import com.gp.socialapp.presentation.post.feed.components.UserName
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.UserCircle


@Composable
fun ResultItemTopRow(
    modifier: Modifier = Modifier,
    imageUrl: String,
    userName: String,
    publishedAt: String,
    onPostAuthorClicked: () -> Unit
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
            onClick = onPostAuthorClicked
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserName(
                userName = userName, onPostAuthorClicked
            )
            PostDate(
                publishedAt = publishedAt,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}