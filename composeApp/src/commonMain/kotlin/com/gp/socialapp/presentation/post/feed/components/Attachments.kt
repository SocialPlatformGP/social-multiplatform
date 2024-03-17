package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.MimeType
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.feed.PostEvent

@Composable
fun Attachments(
    attachments: List<PostFile>,
    onPostEvent: (PostEvent) -> Unit
) {
    val images = attachments.filter {
        it.type in listOf(
            MimeType.JPEG.value,
            MimeType.PNG.value,
            MimeType.GIF.value,
            MimeType.BMP.value,
            MimeType.WEBP.value
        )
    }
    if (images.isNotEmpty()) {
        ImagePager(
            pageCount = images.size,
            images = images,
            width = 300.dp,
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