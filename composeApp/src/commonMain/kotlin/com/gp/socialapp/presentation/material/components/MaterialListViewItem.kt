package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Image(
        imageVector = if (isFolder) MaterialIcon.Folder else getFileImageVector(
            MimeType.getMimeTypeFromFileName(
                name
            )
        ),
        contentDescription = "Folder",
        modifier = Modifier.size(24.dp)
    )
    Text(
        text = name,
        fontSize = 14.sp,
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
    )
    Text(
        text = createdAt.getDateHeader(),
        fontSize = 12.sp,
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(0.5f).padding(horizontal = 8.dp)

    )
    Text(
        text = size,
        fontSize = 12.sp,
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