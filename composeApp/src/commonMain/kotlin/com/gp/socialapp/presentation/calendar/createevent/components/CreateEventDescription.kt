package com.gp.socialapp.presentation.calendar.createevent.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun CreateEventDescription(
    modifier: Modifier = Modifier,
    onDescriptionChanged: (String) -> Unit,
) {
    var description by remember { mutableStateOf("") }
    TextField(value = description, onValueChange = {
        description = it
        onDescriptionChanged(it)
    }, leadingIcon = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Article, contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }, placeholder = { Text("Description") }, modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}