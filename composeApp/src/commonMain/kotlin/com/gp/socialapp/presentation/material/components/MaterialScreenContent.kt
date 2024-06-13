package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.community.source.remote.model.isAdmin
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.MaterialUiState
import com.gp.socialapp.util.SnackbarVisualsWithError
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@Composable
fun MaterialScreenContent(
    state: MaterialUiState,
    action: (MaterialAction) -> Unit,
) {

    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    var dialogState by remember { mutableStateOf(false) }
    var detialsDialogState by remember { mutableStateOf(false) }
    var folderDetialsDialogState by remember { mutableStateOf(false) }
    var fileDetails by remember { mutableStateOf(MaterialFile()) }
    var editFolderNameDialoge by remember { mutableStateOf(false) }
    var folderDetails by remember { mutableStateOf(MaterialFolder()) }
    val snackbarHostState = remember { SnackbarHostState() }
    if (state.error != null) {
        scope.launch {
            snackbarHostState.showSnackbar(
                SnackbarVisualsWithError(
                    message = state.error,
                    isError = true
                )
            )
        }
    }
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
            if(state.isAdmin){
                MaterialFab(filePicker) {
                    dialogState = true
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false
                val buttonColor = if (isError) {
                    ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                } else {
                    ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.inversePrimary
                    )
                }
                Snackbar(
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.secondary)
                        .padding(12.dp),
                    action = {
                        TextButton(
                            onClick = { if (isError) data.dismiss() else data.performAction() },
                            colors = buttonColor
                        ) { Text(data.visuals.actionLabel ?: "") }
                    }
                ) { Text(data.visuals.message) }
            }
        }
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
                        isAdmin = state.isAdmin,
                        folder = folder,
                        action = {
                            when (it) {
                                is MaterialAction.OnFolderDetailsClicked -> {
                                    folderDetialsDialogState = true
                                    folderDetails = it.folder
                                }

                                is MaterialAction.OnRenameFolderClicked -> {
                                    editFolderNameDialoge = true
                                    folderDetails = it.folder
                                }

                                else -> action(it)
                            }
                        }
                    )
                }
                items(state.currentFiles) { file ->
                    FileItem(
                        isAdmin = state.isAdmin,
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
    if (folderDetialsDialogState) {
        FolderDetailsDialog(
            onDismissRequest = { folderDetialsDialogState = false },
            folder = folderDetails,
        )
    }
    if (editFolderNameDialoge) {
        EditFolderName(
            onDismissRequest = { editFolderNameDialoge = false },
            folder = folderDetails,
            onRenameFolderClicked = {
                action(MaterialAction.OnRenameFolderClicked(folderDetails.copy(name = it)))
            }
        )
    }
}