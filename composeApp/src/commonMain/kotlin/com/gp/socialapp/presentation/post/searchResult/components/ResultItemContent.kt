package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.util.AppConstants.BASE_URL
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
fun ResultItemContent(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    attachments: List<PostFile>,
    onImageClicked:(String) -> Unit = {}
) {
    Row (
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ){
        Column{
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if((attachments.firstOrNull()?.type) == FilePickerFileType.ImageContentType){
            val imageURL = BASE_URL+attachments.first().url
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onImageClicked(imageURL)
                    }
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterEnd
            ) {
                AutoSizeBox(imageURL) { action ->
                    when (action) {
                        is ImageAction.Success -> {
                            Image(
                                rememberImageSuccessPainter(action),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center).fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        is ImageAction.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                        is ImageAction.Failure -> {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center),
                            )
                        }
                    }
                }
            }
        }
    }
}