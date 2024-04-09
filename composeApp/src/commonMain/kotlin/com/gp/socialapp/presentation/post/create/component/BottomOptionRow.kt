package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.picker.FilePickerFileType

@Composable
fun BottomOptionRow(
    onAddTagClicked: () -> Unit,
    onAddImageClicked: () -> Unit,
    onAddVideoClicked: () -> Unit,
    onAddFileClicked: () -> Unit,
    pickedFileType: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MyOutlinedTextButton(
            onClick = {
                println("MyOutlinedTextButtonBOR: onClick")
                onAddTagClicked()
            },
            label = "add tags"
        )
        if(pickedFileType.isBlank() || pickedFileType == FilePickerFileType.ImageContentType) {
            MyIconButton(
                onClick = onAddImageClicked,
                icon = Icons.Filled.Image,
            )
        }
        if(pickedFileType.isBlank() || pickedFileType == FilePickerFileType.VideoContentType) {
            MyIconButton(
                onClick = onAddVideoClicked,
                icon = Icons.Filled.VideoFile,
            )
        }
        if(pickedFileType.isBlank() || pickedFileType == FilePickerFileType.AllContentType){
            MyIconButton(
                onClick = onAddFileClicked,
                icon = Icons.Filled.AttachFile,
            )
        }

    }

}



