package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.TypeSpecimen
import androidx.compose.runtime.Composable
import com.gp.socialapp.data.material.model.MaterialFile

@Composable
fun FileDetails(
    file: MaterialFile,
    onOpenLinkClicked: (String) -> Unit
) {
    Column {
        FileDetailsItem(
            "File Name",
            file.name,
            Icons.Default.Title
        )
        FileDetailsItem(
            "File Type",
            file.type,
            Icons.Default.TypeSpecimen
        )
        FileDetailsClickableItem(
            "Url",
            file.url,
            Icons.Default.Link,
            onOpenLinkClicked = onOpenLinkClicked
        )

        FileDetailsItem(
            "Path",
            file.path,
            Icons.Default.Directions
        )
    }
}

