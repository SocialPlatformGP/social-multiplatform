package com.gp.socialapp.presentation.material.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.gp.socialapp.presentation.material.Folder
import com.gp.socialapp.presentation.material.MaterialAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTopAppBar(
    pathes: List<Folder>,
    currentFolder: Folder,
    action: (MaterialAction) -> Unit
) {
    TopAppBar(
        title = {
            val names = pathes.map { it.name }
            Text(names.joinToString(separator = "/") + "/" + currentFolder.name)
        },
        navigationIcon = {
            if (pathes.isNotEmpty())
                IconButton(onClick = { action(MaterialAction.OnBackClicked) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                    )
                }
        }
    )
}