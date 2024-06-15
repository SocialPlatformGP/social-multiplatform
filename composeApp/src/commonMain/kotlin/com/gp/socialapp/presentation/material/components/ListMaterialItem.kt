package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Folder
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.util.LocalDateTimeUtil.getDateHeader


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListMaterialItem(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    isAdmin: Boolean,
    sheetState: SheetState,
    folder: MaterialFolder,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val onMoreClicked: () -> Unit = {
        isMenuExpanded = !isMenuExpanded
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    action(MaterialAction.OnFolderClicked(folder))
                },
                onLongClick = {
                    isMenuExpanded = true
                }
            ), colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = MaterialIcon.Folder,
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        headlineContent = {
                            Text(
                                text = folder.name,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Created " + folder.createdAt.getDateHeader(),
                                style = MaterialTheme.typography.bodySmall, maxLines = 1,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = onMoreClicked,
                                modifier = Modifier.weight(0.1f).padding(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "dropdown menu"
                                )
                            }
                        }
                    )
                }

                else -> {
                    MaterialListViewItem(
                        name = folder.name,
                        isFolder = true,
                        createdAt = folder.createdAt,
                        onMoreClicked = {
                            isMenuExpanded = !isMenuExpanded
                        }
                    )
                }
            }
        }
        if (isMenuExpanded) {
            MaterialMoreMenu(
                isAdmin = isAdmin,
                isExpanded = true,
                windowWidthSizeClass = windowWidthSizeClass,
                onDismiss = { isMenuExpanded = false },
                sheetState = sheetState,
                options = listOf(
                    MaterialOptionItem(
                        label = "Open",
                        onClick = {
                            action(MaterialAction.OnFolderClicked(folder))
                        },
                        isAdminOption = false
                    ),
                    MaterialOptionItem(
                        label = "Delete",
                        onClick = {
                            action(MaterialAction.OnDeleteFolderClicked(folder.id))
                        },
                        isAdminOption = true
                    ),
                    MaterialOptionItem(
                        label = "Rename",
                        onClick = {
                            action(MaterialAction.OnRenameFolderClicked(folder))
                        },
                        isAdminOption = true
                    ),
                    MaterialOptionItem(
                        label = "Details",
                        onClick = {
                            action(MaterialAction.OnFolderDetailsClicked(folder))
                        },
                        isAdminOption = false
                    )
                )
            )
        }
        HorizontalDivider()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListMaterialItem(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    isAdmin: Boolean,
    file: MaterialFile,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val onMoreClicked: () -> Unit = {
        isMenuExpanded = !isMenuExpanded
    }
    Card(modifier = modifier.fillMaxWidth().padding(4.dp).combinedClickable(onClick = {
        action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
    }, onLongClick = {
        isMenuExpanded = true
    }), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = getFileImageVector(
                                    getMimeTypeFromFileName(
                                        file.name
                                    )
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        headlineContent = {
                            Text(
                                text = file.name,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false
                            )
                        },
                        supportingContent = {
                            Text(
                                text = file.size + " - Created " + file.createdAt.getDateHeader(),
                                style = MaterialTheme.typography.bodySmall, maxLines = 1,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = onMoreClicked,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "dropdown menu"
                                )
                            }
                        }
                    )
                }

                else -> {
                    MaterialListViewItem(
                        name = file.name,
                        size = file.size,
                        isFolder = false,
                        createdAt = file.createdAt,
                        onMoreClicked = onMoreClicked
                    )
                }
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
                    MaterialOptionItem(
                        isAdminOption = false,
                        label = "Open",
                        onClick = {
                            action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
                        }
                    ),
                    MaterialOptionItem(
                        isAdminOption = true,
                        label = "Delete",
                        onClick = {
                            action(MaterialAction.OnDeleteFileClicked(file.id))
                        }
                    ),
                    MaterialOptionItem(
                        isAdminOption = false,
                        label = "Copy link",
                        onClick = {
                            action(MaterialAction.OnCopyLinkClicked(file.url))
                        }
                    ),
                    MaterialOptionItem(
                        isAdminOption = false,
                        label = "Download",
                        onClick = {
                            action(
                                MaterialAction.OnDownloadFileClicked(
                                    file.id,
                                    file.url,
                                    file.name
                                )
                            )
                        }
                    ),
                    MaterialOptionItem(
                        isAdminOption = false,
                        label = "Details",
                        onClick = {
                            action(MaterialAction.OnDetailsClicked(file))
                        }
                    ),
                )
            )
        }
        HorizontalDivider()
    }
}


