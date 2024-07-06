package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePreviewDialog(
    modifier: Modifier = Modifier,
    imageURL: String,
    maxWidth: Dp,
    onDismissRequest: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = maxWidth*0.8f),
        ) {
        Card(
            modifier = modifier.padding(16.dp),
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
                                modifier = Modifier.clickable { onDismissRequest() },
                            )
                        }

                        is ImageAction.Failure -> {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center).size(50.dp),
                            )
                        }

                        is ImageAction.Loading -> {
                            CircularProgressIndicator(Modifier.size(36.dp).align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}