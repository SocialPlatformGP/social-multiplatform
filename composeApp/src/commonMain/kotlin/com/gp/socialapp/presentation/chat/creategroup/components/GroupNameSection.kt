package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupNameSection(
    onUpdateName: (String) -> Unit, isError: Boolean, onChangeError: (Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    TextField(
        value = name,
        isError = isError,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        ),
        trailingIcon = {
            Text(
                text = "${name.length} /24",
            )

        },
        supportingText = {
            if (isError) {
                val errorMessage = if(name.isBlank()) "Group name is required" else "Group name must be less than 24 characters"
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        onValueChange = {
            onChangeError(it.isBlank() || name.length > 24)
            name = it
            onUpdateName(it)
        },
        placeholder = {
            Text(
                text = "Group name",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    )

}