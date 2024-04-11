package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gp.socialapp.presentation.post.feed.components.PostDate
import com.gp.socialapp.presentation.post.feed.components.UserImage
import com.gp.socialapp.presentation.post.feed.components.UserName


@Composable
fun ResultItemTopRow(
    modifier: Modifier = Modifier,
    imageUrl: String,
    userName: String,
    publishedAt: String,
) {
    Row(
        modifier = Modifier.background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserImage(imageUrl)
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserName(
                userName = userName,
            )
            PostDate(
                publishedAt = publishedAt,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}