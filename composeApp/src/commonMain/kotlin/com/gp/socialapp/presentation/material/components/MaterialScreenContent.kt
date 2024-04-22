package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.MaterialUiState
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher

@Composable
fun MaterialScreenContent(
    state: MaterialUiState,
    action: (MaterialAction) -> Unit,
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    var dialogState by remember { mutableStateOf(false) }
    var detialsDialogState by remember { mutableStateOf(false) }
    var fileDetails by remember { mutableStateOf(MaterialFile()) }
    val filePicker = rememberFilePickerLauncher(
        selectionMode = FilePickerSelectionMode.Single,
        type = FilePickerFileType.All
    ) { files ->
        uploadFiles(scope, files, context, action)
    }

    Scaffold(
        topBar = {
            MaterialTopAppBar(state.listOfPreviousFolder, state.currentFolder, action)
        },
        floatingActionButton = {
            MaterialFab(filePicker) {
                dialogState = true
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            if (state.isLoading)
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(state.currentFolders) { folder ->
                    FolderItem(
                        folder
                    ) { action(MaterialAction.OnFolderClicked(it)) }
                }
                items(state.currentFiles) { file ->
                    FileItem(
                        file = file,
                        action = {
                            when (it) {
                                is MaterialAction.OnDetailsClicked -> {
                                    detialsDialogState = true
                                    fileDetails = file
                                }

                                else -> action(it)
                            }
                        }
                    )
                }
            }
        }
    }
    if (dialogState) {
        CreateFolderDialog(
            onDismissRequest = { dialogState = false },
            onConfirmation = { action(MaterialAction.OnCreateFolderClicked(it)) }
        )
    }
    if (detialsDialogState) {
        DetailsDialog(
            onDismissRequest = { detialsDialogState = false },
            file = fileDetails,
            onOpenLinkClicked = { action(MaterialAction.OpenLink(it)) }
        )
    }
}