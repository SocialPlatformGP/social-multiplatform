package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Folder
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import java.awt.SystemColor.text


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GridMaterialItem(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    isAdmin: Boolean,
    folder: MaterialFolder,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth().padding(4.dp).combinedClickable(onClick = {
            action(MaterialAction.OnFolderClicked(folder))
        }, onLongClick = {
            isMenuExpanded = true
        })

    ) {
        Column(
            modifier = Modifier.padding( 4.dp)
        ) {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    CompactGridMaterialItem(
                        imageVector = MaterialIcon.Folder,
                        name = folder.name,
                        onMoreClicked = {
                            isMenuExpanded = !isMenuExpanded
                        }
                    )
                }

                else -> {
                    ExpandedGridMaterialItem(
                        isFolder = true,
                        name = folder.name,
                        onMoreClicked = {
                            isMenuExpanded = !isMenuExpanded
                        }
                    )
                }
            }
            if (isMenuExpanded) {
                MaterialMoreMenu(
                    isAdmin = isAdmin,
                    isExpanded = true,
                    sheetState = sheetState,
                    onDismiss = { isMenuExpanded = false },
                    options = listOf(
                        MaterialOptionItem(
                            label = "Open", onClick = {
                                action(MaterialAction.OnFolderClicked(folder))
                            }, isAdminOption = false
                        ), MaterialOptionItem(
                            label = "Delete", onClick = {
                                action(MaterialAction.OnDeleteFolderClicked(folder.id))
                            }, isAdminOption = true
                        ), MaterialOptionItem(
                            label = "Rename", onClick = {
                                action(MaterialAction.OnRenameFolderClicked(folder))
                            }, isAdminOption = true
                        ), MaterialOptionItem(
                            label = "Details", onClick = {
                                action(MaterialAction.OnFolderDetailsClicked(folder))
                            }, isAdminOption = false
                        )
                    ),
                    windowWidthSizeClass = windowWidthSizeClass
                )
            }
        }
    }
}

@Composable
fun ExpandedGridMaterialItem(
    modifier: Modifier = Modifier,
    isFolder: Boolean,
    name: String,
    onMoreClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isFolder) MaterialIcon.Folder else getFileImageVector(
                MimeType.getMimeTypeFromFileName(
                    name
                )
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = if(name.length > 15) name.substring(0, 12) + "..." else name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onMoreClicked,
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "dropdown menu"
            )
        }
    }
}

@Composable
fun CompactGridMaterialItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    name: String,
    onMoreClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().size(64.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(4f)
            )
            IconButton(
                onClick = onMoreClicked,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = null
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GridMaterialItem(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    isAdmin: Boolean,
    file: MaterialFile,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(modifier = modifier.fillMaxWidth().padding(4.dp).combinedClickable(onClick = {
        action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
    }, onLongClick = {
        isMenuExpanded = true
    })) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    CompactGridMaterialItem(
                        imageVector = getFileImageVector(MimeType.getMimeTypeFromFileName(file.name)),
                        name = file.name,
                        onMoreClicked = {
                            isMenuExpanded = !isMenuExpanded
                        }
                    )
                }

                else -> {
                    ExpandedGridMaterialItem(
                        isFolder = false,
                        name = file.name,
                        onMoreClicked = {
                            isMenuExpanded = !isMenuExpanded
                        }
                    )
                }
            }
            if (isMenuExpanded) {
                MaterialMoreMenu(
                    isExpanded = true,
                    isAdmin = isAdmin,
                    sheetState = sheetState,
                    windowWidthSizeClass = windowWidthSizeClass,
                    onDismiss = { isMenuExpanded = false },
                    options = listOf(
                        MaterialOptionItem(isAdminOption = false, label = "Open", onClick = {
                            action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
                        }),
                        MaterialOptionItem(isAdminOption = true, label = "Delete", onClick = {
                            action(MaterialAction.OnDeleteFileClicked(file.id))
                        }),
                        MaterialOptionItem(isAdminOption = false, label = "Copy link", onClick = {
                            action(MaterialAction.OnCopyLinkClicked(file.url))
                        }),
                        MaterialOptionItem(isAdminOption = false, label = "Download", onClick = {
                            action(
                                MaterialAction.OnDownloadFileClicked(
                                    file.id, file.url, file.name
                                )
                            )
                        }),
                        MaterialOptionItem(isAdminOption = false, label = "Details", onClick = {
                            action(MaterialAction.OnDetailsClicked(file))
                        }),
                    )
                )
            }
        }
    }
}