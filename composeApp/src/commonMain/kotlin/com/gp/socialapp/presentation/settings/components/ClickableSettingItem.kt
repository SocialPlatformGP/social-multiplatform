package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.chat.groupdetails.components.ConfirmActionAlertDialog

@Composable
fun ClickableSettingItem(
    modifier: Modifier = Modifier,
    needConfirmation: Boolean = false,
    confirmationTitle: String = "",
    confirmationMessage: String = "",
    title: String,
    description: String = "",
    onClick: () -> Unit
) {
    var isDialogShown by remember {
        mutableStateOf(false)
    }
    if (isDialogShown) {
        ConfirmActionAlertDialog(
            dialogTitle = confirmationTitle,
            dialogText = confirmationMessage,
            onConfirmation = {
                isDialogShown = false
                onClick()
            },
            onDismissRequest = {
                isDialogShown = false
            },
        )
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent,
        onClick = { if (needConfirmation) isDialogShown = true else onClick() },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = { if (needConfirmation) isDialogShown = true else onClick() },
            ) {
                Text(title)
            }
            HorizontalDivider()
        }
    }
}
