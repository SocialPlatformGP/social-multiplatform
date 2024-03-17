package com.gp.material.presentation

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.material.model.FileType
import com.gp.material.model.MaterialItem
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.material.MaterialScreenModel
import com.gp.socialapp.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import socialmultiplatform.composeapp.generated.resources.Res

object MaterialScreen:Screen {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val screenModel = navigator.getNavigatorScreenModel<MaterialScreenModel>()
           // val state by screenModel.items.collectAsState()
            val state=listOf(
                MaterialItem(
                    name = "Folder 1",
                    fileType = FileType.FOLDER,
                    size = "N/A",
                    createdBy = "User1",
                    creationTime = "2022-01-01 12:00:00",
                    path = "/materials/folder1"
                ),
                MaterialItem(
                    name = "Folder 2",
                    fileType = FileType.FOLDER,
                    size = "N/A",
                    createdBy = "User2",
                    creationTime = "2022-01-02 12:00:00",
                    path = "/materials/folder2"
                ),
                MaterialItem(
                    name = "File PDF",
                    fileType = FileType.PDF,
                    size = "2 MB",
                    createdBy = "User3",
                    creationTime = "2022-01-03 12:00:00",
                    path = "/materials/filepdf.pdf"
                ),
                MaterialItem(
                    name = "File Video",
                    fileType = FileType.VIDEO,
                    size = "6.5 MB",
                    createdBy = "User4",
                    creationTime = "2022-01-04 12:00:00",
                    path = "/materials/filevideo.mp4"
                ),
                MaterialItem(
                    name = "File Image",
                    fileType = FileType.IMAGE,
                    size = "6.5 MB",
                    createdBy = "User5",
                    creationTime = "2022-01-05 12:00:00",
                    path = "/materials/fileimage.jpg"
                )
            )
            val currentPath by screenModel.currentPath.collectAsState()
            val isLoading by screenModel.isLoading.collectAsState()
            var isCreateDialogOpen by remember { mutableStateOf(false) }
            var isFileDetailsDialogOpen by remember { mutableStateOf(false) }
            var fileWithOpenDetails by remember { mutableStateOf(MaterialItem()) }
            val folderDropDownItems = listOf("Delete")
            val fileDropDownItems = listOf("Download", "Share", "Details", "Delete")
            val onDropDownItemClicked: (String, MaterialItem) -> Unit = { dropDownItem, item ->
                if (item.fileType == FileType.FOLDER) {
                    if (dropDownItem == "Delete") {
                        screenModel.deleteFolder(item.path)
                    }
                } else {
                    when (dropDownItem) {
                        "Delete" -> {
                            screenModel.deleteFile(item.path)
                        }

                        "Download" -> {
                           // onDownloadFile(item)
                        }

                        "Share" -> {
                           // onShareLink(item)
                        }

                        "Details" -> {
                            fileWithOpenDetails = item
                            isFileDetailsDialogOpen = true
                        }
                    }
                }
            }
            MaterialTheme{

                MaterialContent(
                    currentPath = currentPath,
                    onBackPressed ={} //onBackPressed
                    ,
                    currentFolderName = screenModel.getCurrentFolderName(currentPath),
                    isAdmin = true//isAdmin
                    ,
                    isCreateDialogOpen = isCreateDialogOpen,
                    onShowCreateDialog = { isCreateDialogOpen = true },
                    onDismissCreateDialog = { isCreateDialogOpen = false },
                    onNewFileClicked = { }//onNewFileClicked
                    ,
                    items = state,
                    onFolderClicked = { }//onFolderClicked
                    ,
                    onOpenFile ={ } //onOpenFile
                    ,
                    folderDropDownItems = folderDropDownItems,
                    fileDropDownItems = fileDropDownItems,
                    onDropDownItemClicked = onDropDownItemClicked,
                    isLoading = isLoading,
                    onUploadFolder = screenModel::uploadFolder,
                    isFileDetailsDialogOpen = isFileDetailsDialogOpen,
                    onDismissFileDetailsDialog = { isFileDetailsDialogOpen = false },
                    fileWithOpenDetails = fileWithOpenDetails
                )
            }

        }

}

@Composable
fun MaterialContent(
    modifier: Modifier = Modifier,
    currentPath: String,
    onBackPressed: () -> Unit,
    currentFolderName: String,
    isAdmin: Boolean,
    isCreateDialogOpen: Boolean,
    onShowCreateDialog: () -> Unit,
    onDismissCreateDialog: () -> Unit,
    onNewFileClicked: () -> Unit,
    items: List<MaterialItem>,
    onFolderClicked: (String) -> Unit,
    onOpenFile: (MaterialItem) -> Unit,
    folderDropDownItems: List<String>,
    fileDropDownItems: List<String>,
    onDropDownItemClicked: (String, MaterialItem) -> Unit,
    isLoading: Boolean,
    onUploadFolder: (String) -> Unit,
    isFileDetailsDialogOpen: Boolean,
    onDismissFileDetailsDialog: () -> Unit,
    fileWithOpenDetails: MaterialItem,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = modifier.fillMaxSize(),
        topBar = {
            Card(
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if ((currentPath != "materials") && (currentPath != "/materials")) {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                    Text(
                        text = currentFolderName,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        floatingActionButton = {
            if (isAdmin) {
                MultiFloatingActionButton(
                    fabIcon = Icons.Filled.Add,
                    items = arrayListOf(
                        FabItem(
                            icon = painterResource(resource = Res.drawable.add_file),
                            label = "Create Folder",
                            backgroundColor = Color.White,
                            onFabItemClicked = onShowCreateDialog
                        ),
                        FabItem(
                            icon = painterResource(resource = Res.drawable.upload),
                            label = "Upload File",
                            backgroundColor = Color.White,
                            onFabItemClicked = onNewFileClicked
                        )
                    ),
                )
            }
        },
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items) { item ->
                    MaterialItem(
                        item = item,
                        onItemClick = {
                            if (item.fileType == FileType.FOLDER) {
                                onFolderClicked(item.path)
                            } else {
                                onOpenFile(item)
                            }
                        },
                        dropDownItems = if (item.fileType == FileType.FOLDER) folderDropDownItems else fileDropDownItems,
                        onDropDownItemClicked = onDropDownItemClicked
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 48.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            if (isCreateDialogOpen) {
                CreateFolderDialog(
                    onConfirmation = {
                        onUploadFolder(it)
                        onDismissCreateDialog()
                    },
                    onDismissRequest = onDismissCreateDialog
                )
            }
            if (isFileDetailsDialogOpen) {
                FileDetailsDialog(
                    onDismiss = onDismissFileDetailsDialog,
                    onOpenFile = {
                        onOpenFile(fileWithOpenDetails)
                        onDismissFileDetailsDialog()
                    },
                    file = fileWithOpenDetails
                )
            }
        }
    }
}

@Composable
fun MaterialItem(
    modifier: Modifier = Modifier,
    item: MaterialItem,
    onItemClick: () -> Unit,
    onDropDownItemClicked: (String, MaterialItem) -> Unit,
    dropDownItems: List<String>,
) {
    var isDropDownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset(333.dp, 33.dp))
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
                .indication(interactionSource = interactionSource, LocalIndication.current)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            isDropDownMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onTap = {
                            onItemClick()
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        })
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = getItemPainterResource(item.fileType),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.widthIn(max = 260.dp)
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.size,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        isDropDownMenuVisible = true
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = null
                    )
                }
            }
        }
        DropdownMenu(
            expanded = isDropDownMenuVisible,
            onDismissRequest = {
                isDropDownMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropDownItems.forEach { dropDownItem ->
                DropdownMenuItem(
                    text = {
                        Text(text = dropDownItem)
                    },
                    onClick = {
                        isDropDownMenuVisible = false
                        onDropDownItemClicked(dropDownItem, item)
                    })
            }

        }
    }
}

@Composable
fun getItemPainterResource(type: FileType): Painter {
    return painterResource(
        resource = when (type) {
            FileType.AUDIO -> Res.drawable.sound
            FileType.EXCEL -> Res.drawable.excel
            FileType.FOLDER -> Res.drawable.folder
            FileType.IMAGE -> Res.drawable.image
            FileType.PDF -> Res.drawable.pdf
            FileType.PPT -> Res.drawable.powerpoint
            FileType.TEXT -> Res.drawable.text
            FileType.VIDEO -> Res.drawable.img_3
            FileType.WORD -> Res.drawable.word
            FileType.ZIP -> Res.drawable.zip
            else -> Res.drawable.file
        }
    )
}

@Composable
fun CreateFolderDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (String) -> Unit,
) {
    var textValue by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(text = "New Folder", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    placeholder = { Text(text = "Folder Name") })
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onConfirmation(textValue) },
                        enabled = textValue.isNotBlank(),
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun FileDetailsDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onOpenFile: (MaterialItem) -> Unit,
    file: MaterialItem,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = getItemPainterResource(file.fileType),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = file.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 32.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailsDialogField(
                        title = "Location",
                        value = file.path.substringBeforeLast("/")
                            .replaceFirst("/materials", "Home")
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DetailsDialogField(
                        title = "Size",
                        value = file.size
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DetailsDialogField(
                        title = "Created By",
                        value = file.createdBy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DetailsDialogField(
                        title = "Created At",
                        value = file.creationTime
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onOpenFile(file) },
                    ) {
                        Text("Open File")
                    }
                }
            }

        }
    }

}

@Composable
fun DetailsDialogField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = value,
            fontSize = 18.sp,
        )
    }
}
