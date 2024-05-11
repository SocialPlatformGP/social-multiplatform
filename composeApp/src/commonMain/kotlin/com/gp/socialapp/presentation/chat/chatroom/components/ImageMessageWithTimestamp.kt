package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImageMessageWithTimestamp(
    modifier: Modifier = Modifier,
    imageURL: String,
    onImageClicked: () -> Unit,
    maxHeight: Dp,
    timestamp: String,
) {
    val density = LocalDensity.current.density
    var width by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(0f) }
    Card(
        shape = RoundedCornerShape(20.dp), modifier = modifier
    ) {
        Box {
            MessageImageAttachment(imageURL = imageURL,
                onImageClicked = { onImageClicked() },
                maxHeight = maxHeight,
                modifier = Modifier.onGloballyPositioned {
                    width = it.size.width / density
                    height = it.size.height / density
                })
            Column(
                Modifier.size(width.dp, height.dp).background(
                    Brush.linearGradient(
                        0.875F to Color.Transparent,
                        1F to Color.DarkGray,
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
            ) {}
            Text(
                text = timestamp,
                fontWeight = FontWeight.W400,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 8.dp),
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}