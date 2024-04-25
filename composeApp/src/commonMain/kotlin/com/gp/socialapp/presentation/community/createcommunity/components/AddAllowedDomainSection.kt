package com.gp.socialapp.presentation.community.createcommunity.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddAllowedDomainSection(
    modifier: Modifier = Modifier,
    onAddAllowedDomain: (String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(32.dp),
            value = name,
            onValueChange = {
                isError = false
                name = it
            },
            placeholder = { Text("Add Allowed Domain") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AlternateEmail,
                    contentDescription = "Add Allowed Domain",
                )
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text("Invalid domain")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.weight(1f).padding(8.dp),
        )
        IconButton(
            onClick = {
                if (name.matches("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())) {
                    onAddAllowedDomain(name)
                    name = ""
                } else {
                    isError = true
                }
            },
            enabled = name.isNotBlank(),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Allowed Domain",
            )
        }
    }
}