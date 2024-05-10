package com.gp.socialapp.presentation.chat.createprivate.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User

@Composable
fun UserName(user: User) {
    Text(
        text = user.name, modifier = Modifier.padding(8.dp)
    )
}