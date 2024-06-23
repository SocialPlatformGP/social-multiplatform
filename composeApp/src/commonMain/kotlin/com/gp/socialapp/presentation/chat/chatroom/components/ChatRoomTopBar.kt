package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar

@Composable
fun ChatRoomTopBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onChatHeaderClicked: () -> Unit,
    isPrivateChat: Boolean,
    chatImageURL: String,
    chatTitle: String
) {
    Card(
        shape = RoundedCornerShape(0.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
        ), modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Navigate Back"
                )
            }
            Row(
                modifier = Modifier.clickable { onChatHeaderClicked() }.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                if (isPrivateChat) {
                    CircularAvatar(
                        imageURL = chatImageURL,
                        size = 45.dp,
                        placeHolderImageVector = Icons.Filled.AccountCircle
                    )
                } else {
                    CircularAvatar(
                        imageURL = chatImageURL,
                        size = 45.dp,
                        placeHolderImageVector = Icons.Filled.Groups
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = chatTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}