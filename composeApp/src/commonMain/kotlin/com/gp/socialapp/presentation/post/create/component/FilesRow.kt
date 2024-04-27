package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.toImageBitmap

@Composable
fun FilesRow(
    modifier: Modifier = Modifier,
    files: List<PostAttachment>,
    onFileDelete: (PostAttachment) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(files) { postFile ->
            Box(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .height(150.dp)
                    .clickable { onFileDelete(postFile) }
            ) {
                if (FilePickerFileType.ImageContentType == postFile.type)
                    Image(
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                        bitmap = postFile.file.toImageBitmap(),
                        contentDescription = null,
                        contentScale = androidx.compose.ui.layout.ContentScale.FillHeight
                    )
                else if (FilePickerFileType.VideoContentType == postFile.type)
                    Icon(
                        imageVector = Icons.Default.VideoLibrary,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                    )
                else
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                    )
            }
        }

    }
}