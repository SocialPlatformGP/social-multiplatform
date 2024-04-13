package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
fun ImagePreviewDialog(
    modifier: Modifier = Modifier,
    imageURL: String,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
            modifier = modifier
                .padding(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                AutoSizeBox(imageURL) { action ->
                    when (action) {
                        is ImageAction.Success -> {
                            Image(
                                painter = rememberImageSuccessPainter(
                                    action = action,
                                    filterQuality = FilterQuality.High,
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .clickable { onDismissRequest() },
                            )
                        }

                        is ImageAction.Failure -> {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(50.dp),
                            )
                        }

                        is ImageAction.Loading -> {
                            CircularProgressIndicator(Modifier.size(50.dp).align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}