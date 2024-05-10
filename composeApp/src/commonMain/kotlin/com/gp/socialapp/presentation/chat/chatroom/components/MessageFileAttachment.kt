package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.FileTypeIcons
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Audio
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Excel
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.File
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Pdf
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Ppt
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Text
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Video
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Word
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.picker.FilePickerFileType

@Composable
fun MessageFileAttachment(
    fileName: String,
    onFileClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        color = Color(0f, 0f, 0f, 0.15f),
        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onFileClicked() }.padding(8.dp)
        ) {
            Icon(
                imageVector = com.gp.socialapp.presentation.material.utils.getFileImageVector(
                    MimeType.getMimeTypeFromFileName(fileName)
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = fileName,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun getFileImageVector(fileType: String): ImageVector {
    return when (fileType) {
        FilePickerFileType.PdfContentType -> FileTypeIcons.Pdf
        FilePickerFileType.AudioContentType -> FileTypeIcons.Audio
        FilePickerFileType.VideoContentType -> FileTypeIcons.Video
        FilePickerFileType.TextContentType -> FileTypeIcons.Text
        FilePickerFileType.WordDocumentContentType -> FileTypeIcons.Word
        FilePickerFileType.PowerPointPresentationContentType -> FileTypeIcons.Ppt
        FilePickerFileType.ExcelSpreadsheetContentType -> FileTypeIcons.Excel
        else -> FileTypeIcons.File
    }
}