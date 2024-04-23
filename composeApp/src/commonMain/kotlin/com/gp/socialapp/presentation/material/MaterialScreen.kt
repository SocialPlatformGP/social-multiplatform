package com.gp.socialapp.presentation.material

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.TypeSpecimen
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.presentation.material.components.CreateFolderDialog
import com.gp.socialapp.presentation.material.components.FileDetailsClickableItem
import com.gp.socialapp.presentation.material.components.FileDetailsItem
import com.gp.socialapp.presentation.material.components.FileItem
import com.gp.socialapp.presentation.material.components.FolderItem
import com.gp.socialapp.presentation.material.components.uploadFiles
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

object MaterialScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<MaterialScreenModel>()
        LifecycleEffect(
            onStarted = {
                screenModel.screenModelScope.launch { screenModel.getMaterial() }
            },
            onDisposed = {}
        )
        val state by screenModel.state.collectAsState()
        MaterialScreenContent(
            state = state,
            action = {
                when (it) {
                    is MaterialAction.OnBackClicked -> {
                        if (state.listOfPreviousFolder.isNotEmpty())
                            screenModel.closeFolder()
                        else
                            navigator.pop()
                    }

                    else -> {
                        screenModel.handleUiEvent(it)
                    }
                }
            },
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = {
                    val names = state.listOfPreviousFolder.map { it.name }
                    Text(names.joinToString(separator = "/") + "/" + state.currentFolder.name)
                },
                navigationIcon = {
                    IconButton(onClick = { action(MaterialAction.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            var fabState by remember { mutableStateOf(false) }
            Column {
                if (fabState) {
                    FloatingActionButton(
                        onClick = {
                            dialogState = true
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
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
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
            onShareLinkClicked = { action(MaterialAction.OnShareLinkClicked(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsDialog(
    onDismissRequest: () -> Unit = {},
    file: MaterialFile,
    onShareLinkClicked: (String) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        modifier = Modifier.wrapContentSize(),
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "File Details",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(8.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                FileDetails(
                    file,
                    onShareLinkClicked = onShareLinkClicked
                )
            }
        }
    }
}

@Composable
fun FileDetails(
    file: MaterialFile,
    onShareLinkClicked: (String) -> Unit
) {
    Column {
        FileDetailsItem(
            "File Name",
            file.name,
            Icons.Default.Title
        )
        FileDetailsItem(
            "File Type",
            file.type,
            Icons.Default.TypeSpecimen
        )
        FileDetailsClickableItem(
            "Url",
            file.url,
            Icons.Default.Link,
            onShareLinkClicked = onShareLinkClicked
        )

        FileDetailsItem(
            "Path",
            file.path,
            Icons.Default.Directions
        )
    }
}

