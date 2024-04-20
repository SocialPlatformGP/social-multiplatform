package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Folder

@Composable
fun FolderItem(folder: MaterialFolder, onFolderClicked: (MaterialFolder) -> Unit) {
    Card(
        onClick = { onFolderClicked(folder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .pointerInput(
                Unit
            ) {
                detectTapGestures(
                    onLongPress = {
                        //todo show options menu
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                imageVector = MaterialIcon.Folder,
                contentDescription = "Folder",
                modifier = Modifier.fillMaxWidth().size(100.dp)
            )
            Text(
                text = "Name: " + folder.name,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
        }
    }

}