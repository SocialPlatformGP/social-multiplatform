package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.material.Folder
import com.gp.socialapp.presentation.material.MaterialAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTopAppBar(
    modifier: Modifier = Modifier,
    paths: List<Folder>,
    currentFolder: Folder,
    selectedMode: Int,
    onChangeSelectedMode: (Int) -> Unit,
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
        Spacer(modifier = Modifier.width(16.dp))
        Text(if(names.isEmpty()) "Home" else names.joinToString(separator = " > ") + " > " + currentFolder.name)
        Spacer(modifier = Modifier.weight(1f))
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