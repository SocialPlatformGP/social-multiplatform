package com.gp.socialapp.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun HomeFab(onFabClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClicked

    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add"
        )
    }
}