package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.MimeType
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.feed.PostEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesBottomSheet(
    modifier: Modifier = Modifier,
    attachments: List<PostFile>,
    onDismiss: () -> Unit,
    state: SheetState,
    onPostEvent: (PostEvent) -> Unit = { },
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = state
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(8.dp),
        ) {
            items(attachments) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.Start)
                        .padding(4.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))

                ) {
                    when (it.type) {
                        in listOf(
                            MimeType.VIDEO.value,
                            MimeType.MKV.value,
                            MimeType.MP4.value,
                            MimeType.MOV.value,
                            MimeType.AVI.value,
                        ) -> {
                            Image(
                                imageVector = Icons.Filled.VideoFile,
                                contentDescription = "video file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnVideoClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Blue.copy(alpha = 0.7f)
                                )
                            )
                        }
                        in listOf(
                            MimeType.IMAGE.value,
                            MimeType.JPEG.value,
                            MimeType.PNG.value,
                            MimeType.GIF.value,
                            MimeType.BMP.value,
                            MimeType.TIFF.value,
                            MimeType.WEBP.value,
                        ) -> {
                            Image(
                                imageVector = Icons.Filled.Image,
                                contentDescription = "image file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnImageClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Green.copy(alpha = 0.7f)
                                )
                            )
                        }
                        in listOf(
                            MimeType.WORD.value,
                            MimeType.DOCX.value,
                            MimeType.EXCEL.value,
                            MimeType.XLSX.value,
                            MimeType.POWERPOINT.value,
                            MimeType.PPTX.value,
                        ) -> {
                            Image(
                                imageVector = Icons.Filled.InsertDriveFile,
                                contentDescription = "document file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnDocumentClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Blue.copy(alpha = 0.7f)
                                )
                            )
                        }
                        MimeType.PDF.value -> {
                            Image(
                                imageVector = Icons.Filled.PictureAsPdf,
                                contentDescription = "pdf file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnDocumentClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Red.copy(alpha = 0.7f)
                                )
                            )
                        }
                        in listOf(
                            MimeType.ZIP.value,
                            MimeType.RAR.value,
                            MimeType.TAR.value,
                        ) -> {
                            Image(
                                imageVector = Icons.Filled.FolderZip,
                                contentDescription = "zip file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnDocumentClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Yellow.copy(alpha = 0.7f)
                                )
                            )
                        }
                        in listOf(
                            MimeType.MP3.value,
                            MimeType.WAV.value,
                            MimeType.FLAC.value,
                        ) -> {
                            Image(
                                imageVector = Icons.Filled.AudioFile,
                                contentDescription = "audio file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnAudioClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Cyan.copy(alpha = 0.7f)
                                )
                            )
                        }
                        else -> {
                            Image(
                                imageVector = Icons.Filled.InsertDriveFile,
                                contentDescription = "document file",
                                modifier = Modifier.size(75.dp)
                                    .clickable { onPostEvent(PostEvent.OnDocumentClicked(it)) },
                                colorFilter = ColorFilter.tint(
                                    Color.Blue.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        verticalArrangement = Arrangement.SpaceAround,
                    ) {
                        Text(
                            text = "Name: " + it.name,
                            maxLines = 1,
                        )
                        Text(
                            text = "Type: " + it.type,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}