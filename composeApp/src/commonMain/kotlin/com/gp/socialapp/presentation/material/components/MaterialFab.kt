package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.picker.FilePickerLauncher

@Composable
fun MaterialFab(
    filePicker: FilePickerLauncher,
    onCreateFolderClicked: () -> Unit
) {
    var fabState by remember { mutableStateOf(false) }
    Column {
        if (fabState) {
            FloatingActionButton(
                onClick = {
                    fabState= fabState.not()
                    onCreateFolderClicked()
                }
            ) {
                Image(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "Add Folder",
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            FloatingActionButton(
                onClick = {
                    fabState = fabState.not()
                    filePicker.launch()
                }
            ) {
                Image(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Add File",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        FloatingActionButton(
            onClick = {
                fabState = !fabState
            }
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Add File/Folder",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}