package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Folder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridFolderItem(
    isAdmin: Boolean,
    folder: MaterialFolder,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    action(MaterialAction.OnFolderClicked(folder))
                },
                onLongClick = {
                    isMenuExpanded = true
                }
            )

    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                imageVector = MaterialIcon.Folder,
                contentDescription = "Folder",
                modifier = Modifier.fillMaxWidth().size(100.dp)
            )
            Text(
                text = folder.name,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
            if (isMenuExpanded) {
                FolderMoreOptionsMenu(
                    isAdmin = isAdmin,
                    isExpanded = true,
                    onCloseMenu = { isMenuExpanded = false },
                    onDelete = { action(MaterialAction.OnDeleteFolderClicked(folder.id)) },
                    onRename = {
                        action(MaterialAction.OnRenameFolderClicked(folder))
                    },
                    onOpen = {
                        action(MaterialAction.OnFolderClicked(folder))
                    },
                    onDetails = {
                        action(MaterialAction.OnFolderDetailsClicked(folder))
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListFolderItem(
    isAdmin: Boolean,
    folder: MaterialFolder,
    action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    action(MaterialAction.OnFolderClicked(folder))
                },
                onLongClick = {
                    isMenuExpanded = true
                }
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                imageVector = MaterialIcon.Folder,
                contentDescription = "Folder",
                modifier = Modifier.fillMaxWidth().size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = folder.name,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = folder.path,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = folder.createdAt.toString(),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,

            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    isMenuExpanded = !isMenuExpanded
                }
            ){
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "dropdown menu"
                )
            }
            if (isMenuExpanded) {
                FolderMoreOptionsMenu(
                    isAdmin = isAdmin,
                    isExpanded = true,
                    onCloseMenu = { isMenuExpanded = false },
                    onDelete = { action(MaterialAction.OnDeleteFolderClicked(folder.id)) },
                    onRename = {
                        action(MaterialAction.OnRenameFolderClicked(folder))
                    },
                    onOpen = {
                        action(MaterialAction.OnFolderClicked(folder))
                    },
                    onDetails = {
                        action(MaterialAction.OnFolderDetailsClicked(folder))
                    },
                )
            }
        }
    }

}
