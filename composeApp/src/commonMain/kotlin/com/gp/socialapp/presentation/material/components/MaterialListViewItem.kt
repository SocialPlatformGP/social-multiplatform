package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Folder
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.util.LocalDateTimeUtil.getDateHeader
import kotlinx.datetime.LocalDateTime

@Composable
fun RowScope.MaterialListViewItem(
    name: String,
    size: String = "",
    isFolder: Boolean,
    createdAt: LocalDateTime,
    onMoreClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
    ) {
        Icon(
            imageVector = if (isFolder) MaterialIcon.Folder else getFileImageVector(
                MimeType.getMimeTypeFromFileName(
                    name
                )
            ),
            tint = Color.Unspecified,
            contentDescription = "Folder",
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
    Text(
        text = createdAt.getDateHeader(),
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(0.5f).padding(horizontal = 8.dp)

    )
    Text(
        text = size,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(0.4f).padding(horizontal = 8.dp)
    )
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