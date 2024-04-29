package com.gp.socialapp.presentation.community.createcommunity.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommunityNameSection(
    modifier: Modifier = Modifier,
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
                Text(
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
            Text(
                text = "Community Name",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
    )
}