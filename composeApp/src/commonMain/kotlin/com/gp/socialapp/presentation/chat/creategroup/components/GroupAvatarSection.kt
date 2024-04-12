package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.login

@Composable
fun GroupAvatarSection(
    avatarURL: String = "",
    avatarByteArray: ByteArray = byteArrayOf(),
    isModifiable: Boolean = false,
    onChoosePhotoClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val contentColor = contentColorFor(backgroundColor)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (avatarURL.isBlank()) {
//            androidx.compose.material3.Icon(
//                painter = painterResource(id = Res.drawable.login),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape),
//                tint = MaterialTheme.colorScheme.outline
//            )
        } else {
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = Uri.parse(avatarURL)
//                ),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//            )
        }
        if (isModifiable) {
            androidx.compose.material3.IconButton(
                onClick = { onChoosePhotoClicked() },
                modifier = Modifier
                    .offset(x = 38.dp, y = 38.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .size(32.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Rounded.Create,
                    contentDescription = null,
                )
            }
        }
    }
}