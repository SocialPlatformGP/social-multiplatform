package com.gp.socialapp.presentation.material

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder


data class MaterialUiState(
    val isLoading: Boolean = false,
    val currentFiles: List<MaterialFile> = emptyList(),
    val currentFolders: List<MaterialFolder> = emptyList(),
    val error: String? = null,
    val currentFolder: Folder = Folder("Home", "Home"),
    val listOfPreviousFolder: List<Folder> = listOf(),
    val currentUser: User = User(),
    val currentCommunity: Community = Community(),
    val isAdmin: Boolean = false
)

data class Folder(
    val path: String,
    val name: String
)