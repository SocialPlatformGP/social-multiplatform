package com.gp.socialapp.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.home.HomeUiAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    closeSheet: () -> Unit,
    sheetState: SheetState,
    action: (HomeUiAction) -> Unit,
    onJoinCommunityClicked: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = closeSheet, sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = {
                    action(HomeUiAction.OnCreateCommunityClicked)
                    closeSheet()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Create Community",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
            HorizontalDivider()
            TextButton(
                onClick = {
                    onJoinCommunityClicked()
                    closeSheet()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Join Community",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }
    }

}