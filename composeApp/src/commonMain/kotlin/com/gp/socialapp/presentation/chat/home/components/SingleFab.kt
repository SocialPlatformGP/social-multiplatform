package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SingleFab(
    fabState: MutableState<Boolean>,
    icon: ImageVector
) {
    FloatingActionButton(
        onClick = {
            fabState.value = !fabState.value
        },
    ) {

        Icon(
            imageVector = icon, contentDescription = null
        )
    }
}