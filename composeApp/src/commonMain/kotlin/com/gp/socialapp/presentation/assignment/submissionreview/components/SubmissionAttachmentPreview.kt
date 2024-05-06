package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
fun SubmissionAttachmentPreview(
    modifier: Modifier = Modifier,
    attachment: AssignmentAttachment?,
) {
    Box(
        modifier = modifier.fillMaxHeight()
    ) {
        if (attachment == null) {
            Text(
                text = attachment?.name ?: "No attachment selected",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            when {
                attachment.type.contains("image") -> {
                    // Image Preview
                    ImagePreview(attachment = attachment)
                }

                attachment.type.contains("pdf") -> {
                    PdfPreview(
                        attachment = attachment
                    )
                }

                else -> {
                    // Other file type
                }
            }
        }
    }
}

@Composable
private fun ImagePreview(
    modifier: Modifier = Modifier, attachment: AssignmentAttachment
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = attachment.name,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(5f, 5f),
                    blurRadius = 5f
                )
            ),
            modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
        )
        AutoSizeBox(
            url = attachment.url,
        ) { action ->
            when (action) {
                is ImageAction.Success -> {
                    Image(
                        painter = rememberImageSuccessPainter(
                            action = action,
                            filterQuality = FilterQuality.High
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                }

                is ImageAction.Failure -> {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp),
                    )
                }

                is ImageAction.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp),
                    )
                }
            }
        }
    }
}

@Composable
expect fun PdfPreview(
    modifier: Modifier = Modifier,
    attachment: AssignmentAttachment
)