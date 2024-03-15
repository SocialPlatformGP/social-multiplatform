package com.gp.socialapp.presentation.post.create.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyIconButton(
    onClick: () -> Unit,
    icon: ImageVector
) {
    IconButton(
        onClick = {
            println("MyIconButton: onClick")
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}