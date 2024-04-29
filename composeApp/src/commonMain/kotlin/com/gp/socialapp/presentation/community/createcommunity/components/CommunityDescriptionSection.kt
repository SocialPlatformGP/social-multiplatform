package com.gp.socialapp.presentation.community.createcommunity.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommunityDescriptionSection(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit,
    description: String
) {
    OutlinedTextField(
        shape = RoundedCornerShape(32.dp),
        value = description,
        onValueChange = {
            onDescriptionChange(it)
        },
        label = {
            Text(
                text = "Community Description",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth(),
    )
}