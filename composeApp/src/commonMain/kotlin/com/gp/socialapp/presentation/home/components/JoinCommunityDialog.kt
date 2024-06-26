package com.gp.socialapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JoinCommunityDialog(
    onDismiss: () -> Unit,
    onJoin: (String) -> Unit
) {
    var codeValue by remember { mutableStateOf("") }
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.People, contentDescription = "Add"
            )
        },
        title = {
            Text(text = "Join Community")
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onJoin(codeValue) }) {
                Text(text = "Join")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Enter the community code", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = codeValue,
                    onValueChange = { codeValue = it },
                    placeholder = { Text(text = "Folder Name") }
                )
            }
        }
    )
}