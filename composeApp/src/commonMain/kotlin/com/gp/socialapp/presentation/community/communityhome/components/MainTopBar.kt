package com.gp.socialapp.presentation.community.communityhome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainTopBar(
    onSearchClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onNavDrawerIconClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                MaterialTheme.colorScheme.onPrimaryContainer
            )
            .padding(16.dp)

    ) {
        IconButton(onClick = {
            onNavDrawerIconClicked()
        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "nav drawer",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(onClick = {
            onSearchClicked()
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = "EduLink",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
        IconButton(onClick = {
            onNotificationClicked()
        }) {
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = "notification",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}