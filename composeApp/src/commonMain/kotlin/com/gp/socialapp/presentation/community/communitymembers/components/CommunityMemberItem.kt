package com.gp.socialapp.presentation.community.communitymembers.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.components.CircleCheckbox
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun CommunityMemberItem(
    modifier: Modifier = Modifier,
    user: User,
    isAdmin: Boolean,
    onUserClicked: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .fillMaxWidth()
            .clickable {
                onUserClicked(user.id)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularAvatar(
            imageURL = user.profilePictureURL,
            size = 55.dp,
            placeHolderImageVector = Icons.Filled.AccountCircle
        )
        Spacer(modifier = modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Column {
                Text(
                    text = user.firstName + " " + user.lastName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                )
                if (user.bio.isNotBlank()) {
                    Text(
                        text = user.bio,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            if (isAdmin) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Admin",
                    modifier= Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}