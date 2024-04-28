package com.gp.socialapp.presentation.material.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FolderMoreOptionsMenu(
    isExpanded: Boolean,
    onCloseMenu: () -> Unit,
    onDelete: () -> Unit,
    onRename: () -> Unit,
    onDetails: () -> Unit,
    onOpen: () -> Unit
) {
    if (isExpanded) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onCloseMenu
        ) {
            DropdownMenuItem(
                text = { Text("Open") },
                onClick = {
                    onOpen()
                    onCloseMenu()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onDelete()
                    onCloseMenu()
                }
            )
            DropdownMenuItem(
                text = { Text("Rename") },
                onClick = {
                    onRename()
                    onCloseMenu()
                }
            )
            DropdownMenuItem(
                text = { Text("Details") },
                onClick = {
                    onDetails()
                    onCloseMenu()
                }

            )

        }
    }
}