package com.gp.socialapp.presentation.chat.private_chat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User

@Composable
fun CreatePrivateChatItem(
    user: User,
    OnUserSelected: () -> Unit
) {
    Card(
        onClick = { OnUserSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserImage(user)
            UserName(user)
        }
    }

}

