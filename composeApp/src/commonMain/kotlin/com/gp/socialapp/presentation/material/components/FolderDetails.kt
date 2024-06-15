package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.util.LocalDateTimeUtil.getDateHeader

@Composable
fun FolderDetails(
    folder: MaterialFolder,
) {
    Column {
        FileDetailsItem(
            "Folder Name",
            folder.name,
            Icons.Default.Title
        )
        FileDetailsItem(
            title = "Created At",
            value = folder.createdAt.getDateHeader(),
            Icons.Default.Schedule
        )
//        FileDetailsItem(
//            "Path",
//            folder.path,
//            Icons.Default.Directions
//        )
    }
}