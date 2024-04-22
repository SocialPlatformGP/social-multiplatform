package com.gp.socialapp.presentation.material

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder


data class MaterialUiState(
    val isLoading: Boolean = false,
    val currentFiles: List<MaterialFile> = emptyList(),
    val currentFolders: List<MaterialFolder> = emptyList(),
    val error: String? = null,
    val currentFolder: Folder = Folder("root", "root"),
    val listOfPreviousFolder: List<Folder> = listOf(),

    )

data class Folder(
    val path: String,
    val name: String
)