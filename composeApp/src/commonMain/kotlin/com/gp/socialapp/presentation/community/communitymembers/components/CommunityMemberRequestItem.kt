package com.gp.socialapp.presentation.community.communitymembers.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun CommunityMemberRequestItem(
    modifier: Modifier = Modifier,
    request: CommunityMemberRequest,
    onUserClicked: (String) -> Unit,
    onAcceptRequest: (String) -> Unit,
    onDeclineRequest: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .fillMaxWidth()
            .clickable {
                onUserClicked(request.userId)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularAvatar(
            imageURL = request.userAvatar,
            size = 55.dp,
            placeHolderImageVector = Icons.Filled.AccountCircle
        )
        Spacer(modifier = modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Column {
//                val name = if(request.userName.length > 13) "${request.userName.substring(0, 13)}..." else request.userName
                Text(
                    text = request.userName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        Row(
            modifier = Modifier
        ) {
            IconButton(
                onClick = { onAcceptRequest(request.userId) },
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = Color.Green,
                    contentDescription = "Admin",
                    modifier = Modifier.padding(8.dp)
                )
            }
            IconButton(
                onClick = { onDeclineRequest(request.userId) },
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}