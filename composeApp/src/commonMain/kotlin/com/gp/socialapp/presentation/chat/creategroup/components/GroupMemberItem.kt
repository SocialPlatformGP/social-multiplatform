package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser
import org.jetbrains.compose.resources.painterResource

@Composable
fun GroupMemberItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isSelectable: Boolean = false,
    selectableUser: SelectableUser,
    isAdmin: Boolean = false,
    onUserClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .fillMaxWidth()
            .clickable {
                onUserClick(selectableUser.user.id)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularAvatar(
            imageURL = selectableUser.user.profilePictureURL,
            size = 55.dp,
            placeHolderImageVector = Icons.Filled.AccountCircle
        )
        Spacer(modifier = modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Column {
                Text(
                    text = selectableUser.user.firstName + " " + selectableUser.user.lastName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                )
                if (selectableUser.user.bio.isNotBlank()) {
                    Text(
                        text = selectableUser.user.bio,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            if (isSelectable) {
                CircleCheckbox(
                    selected = isSelected,
                    onChecked = { onUserClick(selectableUser.user.id) },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            } else if (isAdmin) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Admin",
                    modifier= Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}
