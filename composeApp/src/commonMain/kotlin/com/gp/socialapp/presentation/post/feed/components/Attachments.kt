package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.MimeType
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.mohamedrejeb.calf.picker.FilePickerFileType

@Composable
fun Attachments(
    attachments: List<PostFile>,
    width: Dp,
    onPostEvent: (PostEvent) -> Unit
) {
    val images = attachments.filter {
        it.type == FilePickerFileType.ImageContentType
    }
    if (images.isNotEmpty()) {
        ImagePager(
            pageCount = images.size,
            images = images,
            width = width,
            onImageClicked = { selectedImage ->
                onPostEvent(
                    PostEvent.OnImageClicked(
                        selectedImage
                    )
                )
            },
        )
    }

}