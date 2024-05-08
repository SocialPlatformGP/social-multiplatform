package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.getFileImageVector

@Composable
fun AssignmentAttachmentsList(
    modifier: Modifier = Modifier,
    selectedAttachmentId: String,
    attachments: List<AssignmentAttachment>,
    onAttachmentClicked: (String) -> Unit
) {
    Column (
        modifier = Modifier.then(modifier)
    ){
        Text(text = "Attachments", style = MaterialTheme.typography.titleMedium)
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(attachments.size) { index ->
                AssignmentAttachmentItem(
                    attachment = attachments[index],
                    isSelected = attachments[index].id == selectedAttachmentId,
                    onAttachmentClicked = onAttachmentClicked
                )
            }
        }
    }
}

@Composable
private fun AssignmentAttachmentItem(
    modifier: Modifier = Modifier,
    attachment: AssignmentAttachment,
    isSelected: Boolean,
    onAttachmentClicked: (String) -> Unit
) {
    val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
    val imageVector = getFileImageVector(mimeType)
    Surface (
        modifier = modifier
            .width(
                TextFieldDefaults.MinWidth
            ).clickable {
            onAttachmentClicked(attachment.id)
        },
        tonalElevation = if (isSelected) 8.dp else 0.dp,
        shadowElevation = if (isSelected) 8.dp else 0.dp,
    ){
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = imageVector,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = attachment.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}