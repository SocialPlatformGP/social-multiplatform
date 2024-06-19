package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.material.Folder
import com.gp.socialapp.presentation.material.MaterialAction
import java.io.File.separator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTopAppBar(
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    windowWidthSizeClass: WindowWidthSizeClass,
    paths: List<Folder>,
    currentFolder: Folder,
    selectedMode: Int,
    onChangeSelectedMode: (Int) -> Unit,
    onUploadFileClicked: () -> Unit,
    onCreateFolderClicked: () -> Unit,
    action: (MaterialAction) -> Unit
) {

    Row(
        modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        if (paths.isNotEmpty()) {
            IconButton(onClick = { action(MaterialAction.OnBackClicked) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                )
            }
        }
        val names = paths.map { it.name }
        val title = if (names.isEmpty()) "Home" else if (names.size > 3) names.first()+" > .... > "+currentFolder.name else names.joinToString(separator = " > ") + " > " + currentFolder.name
        Spacer(modifier = Modifier.width(16.dp))
        Text(title)
        Spacer(modifier = Modifier.weight(1f))
        if (isAdmin && windowWidthSizeClass != WindowWidthSizeClass.Compact) {
            TextButton(
                onClick = {
                    onCreateFolderClicked()
                },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CreateNewFolder,
                    contentDescription = "Add Folder",
                )
                Text(
                    "Create Folder",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Button(
                onClick = {
                    onUploadFileClicked()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 4.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = "Add Folder",
                )
                Text(
                    "Upload File",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        SingleChoiceSegmentedButtonRow {
            SegmentedButton(
                selected = selectedMode == 0,
                onClick = { onChangeSelectedMode(0) },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ViewList,
                    contentDescription = "Folders",
                )
            }
            SegmentedButton(
                selected = selectedMode == 1,
                onClick = { onChangeSelectedMode(1) },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            ) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Folders",
                )
            }
        }
    }
}