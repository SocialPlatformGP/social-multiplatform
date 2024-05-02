package com.gp.socialapp.presentation.assignment.createassignment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomAction
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AssignmentAttachmentsSection(
    modifier: Modifier = Modifier,
    attachments: List<AssignmentAttachment>,
    scope: CoroutineScope = rememberCoroutineScope(),
    onAddAttachment: (String, String, Long, ByteArray) -> Unit,
    onRemoveAttachment: (AssignmentAttachment) -> Unit,
) {
    val context = LocalPlatformContext.current
    val filePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.All,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    val bytearray = file.readByteArray(context)
                    val name = file.getName(context) ?: ""
                    val mimeType = MimeType.getMimeTypeFromFileName(name)
                    val extension = MimeType.getExtensionFromMimeType(mimeType)
                    val size = bytearray.size.toLong()
                    onAddAttachment(name, extension, size, bytearray)
                }
            }
        })
    Column (
        modifier = Modifier.fillMaxWidth().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        AssignmentAttachmentsPreview(
            attachments = attachments,
            onRemoveAttachment = onRemoveAttachment
        )
        OutlinedButton(
            onClick = { filePicker.launch() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add attachment")
        }
    }
}

@Composable
private fun AssignmentAttachmentsPreview(
    modifier: Modifier = Modifier,
    attachments: List<AssignmentAttachment>,
    onRemoveAttachment: (AssignmentAttachment) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().heightIn(max = 360.dp).then(modifier)
    ) {
        items(attachments){ attachment ->
            val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
            val imageVector = getFileImageVector(mimeType)
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(
                        text = attachment.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingContent = {
                    IconButton(
                        onClick = { onRemoveAttachment(attachment) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove attachment"
                        )
                    }
                }
            )
            HorizontalDivider(Modifier.padding(horizontal = 24.dp))
        }
    }
}