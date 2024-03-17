package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.MimeType
import com.gp.socialapp.data.post.source.remote.model.PostFile

@Composable
fun AttachmentItem(
    attachment: PostFile,
    onVideoClicked: () -> Unit,
    onAudioClicked: () -> Unit,
    onImageClicked: () -> Unit,
    onDocumentClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 225.dp)
    ) {
        when (attachment.type) {
            in listOf(
                MimeType.VIDEO.value,
                MimeType.MKV.value,
                MimeType.AVI.value,
                MimeType.MP4.value,
                MimeType.MOV.value,
                MimeType.WMV.value
            ) -> {
                Icon(
                    imageVector = Icons.Filled.VideoLibrary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .clickable {
                            onVideoClicked()
                        }
                        .align(Alignment.Center)
                )
            }

            in listOf(
                MimeType.PDF.value,
                MimeType.DOCX.value,
                MimeType.WORD.value,
                MimeType.EXCEL.value,
                MimeType.XLSX.value,
                MimeType.POWERPOINT.value,
                MimeType.PPTX.value,
            ) -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .clickable {
                            onDocumentClicked()
                        }
                        .align(Alignment.Center)
                )
            }

            in listOf(
                MimeType.AUDIO.value,
                MimeType.MP3.value,
                MimeType.AAC.value,
                MimeType.WAV.value,
                MimeType.OGG.value,
                MimeType.FLAC.value
            ) -> {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .clickable {
                            onAudioClicked()
                        }
                        .align(Alignment.Center)
                )
            }
            in listOf (
                MimeType.IMAGE.value,
                MimeType.JPEG.value,
                MimeType.PNG.value,
                MimeType.GIF.value,
                MimeType.BMP.value,
                MimeType.TIFF.value,
                MimeType.WEBP.value
            ) -> {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .clickable {
                            onImageClicked()
                        }
                        .align(Alignment.Center)
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 200.dp, height = 300.dp)
                        .clickable {
                            onAudioClicked()
                        }
                        .align(Alignment.Center)
                )

            }
        }
    }
}