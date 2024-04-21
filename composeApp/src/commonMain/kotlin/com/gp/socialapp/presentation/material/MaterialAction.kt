package com.gp.socialapp.presentation.material

import com.gp.socialapp.data.material.model.MaterialFolder

sealed interface MaterialAction {
    data class OnCreateFolderClicked(val name: String) : MaterialAction
    data class OnFolderClicked(val folder: MaterialFolder) : MaterialAction
    data class OnFileClicked(val fileId: String, val url: String, val fileName: String) :
        MaterialAction

    data object OnBackClicked : MaterialAction
    data class OnDeleteFileClicked(val fileId: String) : MaterialAction
    data class OnDeleteFolderClicked(val folderId: String) : MaterialAction
    data class OnUploadFileClicked(val name: String, val type: String, val content: ByteArray) :
        MaterialAction

    data class OnDownloadFileClicked(val url: String, val fileName: String) : MaterialAction
}