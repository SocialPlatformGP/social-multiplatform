package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.feed.PostEvent

@Composable
fun ButtonViewFiles(
    attachments: List<PostFile>,
    postEvent: (PostEvent) -> Unit
) {
    OutlinedButton(
        onClick = {
            postEvent(
                PostEvent.OnViewFilesAttachmentClicked(attachments)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
//        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "+ ${attachments.size} File Attached",
//            color = MaterialTheme.colors.primary,
//            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}