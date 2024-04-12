package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GroupNameSection(
    name: String,
    onUpdateName: (String) -> Unit,
    isError: Boolean,
    onChangeError: (Boolean) -> Unit
) {
    OutlinedTextField(
        shape = RoundedCornerShape(32.dp),
        value = name,
        isError = isError,
        supportingText = {
            if (isError) {
                androidx.compose.material3.Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Group Name is Required",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        onValueChange = {
            onChangeError(false)
            onUpdateName(it)
        },
        label = {
            androidx.compose.material3.Text(
                text = "Group Name",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    )

}