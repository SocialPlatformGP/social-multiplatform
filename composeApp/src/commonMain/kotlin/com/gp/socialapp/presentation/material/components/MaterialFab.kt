package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.picker.FilePickerLauncher
import kotlinx.serialization.json.JsonNull.content

@Composable
fun MaterialFab(
    filePicker: FilePickerLauncher,
    windowWidthSizeClass: WindowWidthSizeClass,
    onCreateFolderClicked: () -> Unit
) {
    var fabState by remember { mutableStateOf(false) }
    Column {
        if (fabState) {
            AdaptiveFab(
                windowWidthSizeClass = windowWidthSizeClass,
                onClick = {
                    fabState = fabState.not()
                    onCreateFolderClicked()
                },
                compactContent = {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Add Folder",
                    )
                },
                extendedContent = {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Add Folder",
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("Create Folder")
                }
            )
            Spacer(modifier = Modifier.size(4.dp))
            AdaptiveFab(
                windowWidthSizeClass = windowWidthSizeClass,
                onClick = {
                    fabState = fabState.not()
                    filePicker.launch()
                },
                compactContent = {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Add File",
                    )
                },
                extendedContent = {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Add File",
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("Upload File")
                }
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        AdaptiveFab(
            windowWidthSizeClass = windowWidthSizeClass,
            onClick = {
                fabState = !fabState
            },
            compactContent = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add File/Folder",
                )
            },
            extendedContent = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add File/Folder",
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text("Create")
            }
        )
    }
}

@Composable
fun AdaptiveFab(
    onClick: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    compactContent: @Composable () -> Unit,
    extendedContent: @Composable RowScope.() -> Unit
) {
    if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        FloatingActionButton(
            onClick = onClick,
            content = compactContent
        )
    } else {
        ExtendedFloatingActionButton(
            onClick = onClick,
            content = extendedContent,
        )
    }
}