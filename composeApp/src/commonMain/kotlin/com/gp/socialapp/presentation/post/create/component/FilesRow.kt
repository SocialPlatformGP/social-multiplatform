package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.mohamedrejeb.calf.picker.toImageBitmap

@Composable
fun FilesRow(
    files: List<PostAttachment>,
    onFileClick: (PostAttachment) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(files) { postFile ->
            Box(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .height(150.dp)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = postFile.file.toImageBitmap(),
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.FillHeight
                )
            }
        }

    }
}