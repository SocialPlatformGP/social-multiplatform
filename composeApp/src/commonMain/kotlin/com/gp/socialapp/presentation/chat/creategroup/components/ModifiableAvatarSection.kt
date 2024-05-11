package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ModifiableAvatarSection(
    avatarURL: String = "",
    avatarByteArray: ByteArray = byteArrayOf(),
    isModifiable: Boolean = false,
    onImagePicked: (ByteArray, String) -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    context: PlatformContext = LocalPlatformContext.current,
) {
    val imagePicker = rememberFilePickerLauncher(type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    val image = file.readByteArray(context)
                    val name = file.getName(context) ?: ""
                    val mimeType = MimeType.getMimeTypeFromFileName(name)
                    val extension = MimeType.getExtensionFromMimeType(mimeType)
                    onImagePicked(image, extension)
                }
            }
        })
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()
    ) {
        val imageModifier = Modifier.size(100.dp).align(Alignment.Center).clip(CircleShape)
        if (avatarByteArray.isEmpty() && avatarURL.isBlank()) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = imageModifier,
                tint = MaterialTheme.colorScheme.outline
            )
        } else if (avatarURL.isBlank()) {
            val bitmap = avatarByteArray.toImageBitmap()
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AutoSizeBox(avatarURL) { action ->
                when (action) {
                    is ImageAction.Success -> {
                        Image(
                            rememberImageSuccessPainter(action),
                            contentDescription = null,
                            modifier = imageModifier,
                        )
                    }

                    is ImageAction.Loading -> {
                        CircularProgressIndicator(modifier = imageModifier)
                    }

                    is ImageAction.Failure -> {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = null,
                            modifier = imageModifier,
                        )
                    }

                    else -> Unit
                }
            }
        }
        if (isModifiable) {
            IconButton(
                onClick = { imagePicker.launch() },
                modifier = Modifier.offset(x = 38.dp, y = 38.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Create,
                    contentDescription = null,
                )
            }
        }
    }
}