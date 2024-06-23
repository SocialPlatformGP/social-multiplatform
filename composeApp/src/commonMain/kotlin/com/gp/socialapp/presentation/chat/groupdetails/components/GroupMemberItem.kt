package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun GroupMemberItem(
    modifier: Modifier = Modifier,
    user: User,
    onUserClicked: () -> Unit,
    isAdmin: Boolean = false,
) {
    ListItem(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onUserClicked() },
        leadingContent = {
            CircularAvatar(
                imageURL = user.profilePictureURL,
                size = 48.dp,
                placeHolderImageVector = Icons.Filled.AccountCircle,
            )
        },
        headlineContent = {
            Text(
                text = user.name,
                fontSize = 18.sp,
            )
        },
        trailingContent = {
            if (isAdmin) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Admin",
                )
            }
        },
        supportingContent = {
            if(user.bio.isNotBlank()){
                Text(
                    text = user.bio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }
        }
    )
}