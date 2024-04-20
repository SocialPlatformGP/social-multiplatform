package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RectangleFab(
    fabState: MutableState<Boolean>,
    onClick: () -> Unit,
    text: String
) {
    FloatingActionButton(
        onClick = {
            fabState.value = !fabState.value
            onClick()
        }, modifier = Modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}