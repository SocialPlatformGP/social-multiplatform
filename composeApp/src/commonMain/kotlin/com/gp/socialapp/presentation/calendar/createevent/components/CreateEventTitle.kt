package com.gp.socialapp.presentation.calendar.createevent.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateEventTitle(
    modifier: Modifier = Modifier,
    onTitleChanged: (String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    TextField(
        value = title,
        onValueChange = {
            title = it
            onTitleChanged(it)
        },
        placeholder = { Text("Title") },
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}