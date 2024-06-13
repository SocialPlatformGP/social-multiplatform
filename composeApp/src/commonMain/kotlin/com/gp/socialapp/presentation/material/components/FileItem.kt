package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.presentation.material.utils.getFileImageVector

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridFileItem(
    modifier: Modifier = Modifier,
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
            Image(
                imageVector = getFileImageVector(
                    getMimeTypeFromFileName(
                        file.name
                    )
                ), contentDescription = "File", modifier = Modifier.fillMaxWidth().size(100.dp)
            )
            Text(
                text = file.name,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
            if (isMenuExpanded) {
                FileMoreOptionsMenu(isAdmin = isAdmin,
                    isExpanded = true,
                    onCloseMenu = { isMenuExpanded = false },
                    onDelete = { action(MaterialAction.OnDeleteFileClicked(file.id)) },
                    onOpenFile = {
                        action(
                            MaterialAction.OnFileClicked(
                                file.id, file.url, file.name
                            )
                        )
                    },
                    onDownload = {
                        action(
                            MaterialAction.OnDownloadFileClicked(
                                file.id, file.url, file.name
                            )
                        )
                    },
                    onDetails = {
                        action(MaterialAction.OnDetailsClicked(file))
                    },
                    onCopyLinkClicked = {
                        action(MaterialAction.OnCopyLinkClicked(file.url))
                    })
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListFileItem(
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    file: MaterialFile,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(modifier = modifier.fillMaxWidth().padding(4.dp).combinedClickable(onClick = {
        action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
    }, onLongClick = {
        isMenuExpanded = true
    }), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            MaterialListViewItem(name = file.name,
                size = file.size,
                isFolder = false,
                createdAt = file.createdAt,
                onMoreClicked = {
                    isMenuExpanded = !isMenuExpanded
                })
            if (isMenuExpanded) {
                FileMoreOptionsMenu(isAdmin = isAdmin,
                    isExpanded = true,
                    onCloseMenu = { isMenuExpanded = false },
                    onDelete = { action(MaterialAction.OnDeleteFileClicked(file.id)) },
                    onOpenFile = {
                        action(
                            MaterialAction.OnFileClicked(
                                file.id, file.url, file.name
                            )
                        )
                    },
                    onDownload = {
                        action(
                            MaterialAction.OnDownloadFileClicked(
                                file.id, file.url, file.name
                            )
                        )
                    },
                    onDetails = {
                        action(MaterialAction.OnDetailsClicked(file))
                    },
                    onCopyLinkClicked = {
                        action(MaterialAction.OnCopyLinkClicked(file.url))
                    })
            }
        }
    }
}


