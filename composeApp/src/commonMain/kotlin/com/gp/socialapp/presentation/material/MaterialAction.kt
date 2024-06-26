package com.gp.socialapp.presentation.material

import com.gp.socialapp.data.material.model.MaterialFile
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

    data class OnDownloadFileClicked(val id: String, val url: String, val fileName: String) : MaterialAction
    data class OnDetailsClicked(val file: MaterialFile) : MaterialAction
    data class OnCopyLinkClicked(val url: String) : MaterialAction
    data class OpenLink(val link: String) : MaterialAction
    data class OnRenameFileClicked(val fileId: String, val newName: String) : MaterialAction
    data class OnRenameFolderClicked(val folder: MaterialFolder) : MaterialAction
    data class OnFolderDetailsClicked(val folder: MaterialFolder) : MaterialAction

}