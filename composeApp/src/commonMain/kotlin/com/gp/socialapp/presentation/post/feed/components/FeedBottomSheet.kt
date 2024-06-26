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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.presentation.post.feed.PostEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesBottomSheet(
    modifier: Modifier = Modifier,
    attachments: List<PostAttachment>,
    onDismiss: () -> Unit,
    state: SheetState,
    onPostEvent: (PostEvent) -> Unit = { },
) {

    ModalBottomSheet(
        modifier = modifier,
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
                    val mimeType = MimeType.getMimeTypeFromFileName(it.name)
                    Image(
                        imageVector = getFileImageVector(mimeType),
                        contentDescription = "file",
                        modifier = Modifier.size(75.dp)
                            .clickable { onPostEvent(PostEvent.OnAttachmentClicked(it)) },
                    )

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