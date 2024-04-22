package com.gp.socialapp.presentation.material.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MoreOptionsMenu(
    isExpanded: Boolean,
    onCloseMenu: () -> Unit,
    onDelete: () -> Unit,
    onOpenFile: () -> Unit,
    onDownload: () -> Unit,
    onDetails: () -> Unit,
    onShareFileClicked: () -> Unit
) {
    if (isExpanded) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onCloseMenu
        ) {
            DropdownMenuItem(
                text = { Text("Open") },
                onClick = {
                    onOpenFile()
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
                text = { Text("Share") },
                onClick = {
                    onShareFileClicked()
                    onCloseMenu()
                }
            )
            DropdownMenuItem(
                text = { Text("Download") },
                onClick = {
                    onDownload()
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