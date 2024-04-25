package com.gp.socialapp.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.home.HomeUiAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    user: User, action: (HomeUiAction) -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "Hello, ${user.firstName}",
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, navigationIcon = {
        IconButton(onClick = {
            action(HomeUiAction.OnUserLogout)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout"
            )
        }

    })
}