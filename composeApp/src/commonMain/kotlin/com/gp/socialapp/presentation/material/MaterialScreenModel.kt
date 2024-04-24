package com.gp.socialapp.presentation.material


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.repository.MaterialRepository
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MaterialScreenModel(
    private val materialRepo: MaterialRepository
) : ScreenModel {
    private val uiState = MutableStateFlow(MaterialUiState())
    val state = uiState.asStateFlow()

    fun getMaterial() {
        screenModelScope.launch {
            materialRepo.getMaterialAtPath(state.value.currentFolder.path).collect { result ->
                handleUiState(result)
            }
        }
    }


    private fun uploadFolder(
        folderName: String
    ) {
        screenModelScope.launch {
            materialRepo.createFolder(
                name = folderName,
                path = state.value.currentFolder.path,
            ).collect { result ->
                handleUiState(result)
            }
        }
    }

    private fun uploadFile(
        fileName: String, fileType: String, fileContent: ByteArray
    ) {
        screenModelScope.launch {
            materialRepo.createFile(
                name = fileName,
                type = fileType,
                path = state.value.currentFolder.path,
                content = fileContent
            ).collect { result ->
                handleUiState(result)
            }
        }
    }

    private fun openFolder(folder: MaterialFolder) {
        val newFolder = Folder(
            path = folder.id, name = folder.name
        )
        uiState.update {
            it.copy(
                listOfPreviousFolder = state.value.listOfPreviousFolder.plus(state.value.currentFolder),
                currentFolder = newFolder,

                )
        }.apply {
            getMaterial()
        }
    }

    fun closeFolder() {
        uiState.update {
            it.copy(
                currentFolder = state.value.listOfPreviousFolder.last(),
            )
        }.also {
            uiState.update {
                it.copy(
                    listOfPreviousFolder = state.value.listOfPreviousFolder.dropLast(1)
                )
            }.also {
                getMaterial()
            }
        }
    }

    private fun deleteFile(fileId: String) {
        screenModelScope.launch {
            materialRepo.deleteFile(fileId, state.value.currentFolder.path).collect { result ->
                handleUiState(result)
            }
        }
    }

    private fun deleteFolder(folderId: String) {
        screenModelScope.launch {
            materialRepo.deleteFolder(folderId).collect { result ->
                handleUiState(result)
            }
        }
    }

    private fun downloadFile(url: String, mimeType: MimeType) {
        screenModelScope.launch(DispatcherIO) {
            materialRepo.downloadFile(url, mimeType.mimeType)
        }
    }

    fun handleUiEvent(event: MaterialAction) {
        when (event) {
            is MaterialAction.OnUploadFileClicked -> uploadFile(
                event.name, event.type, event.content
            )

            is MaterialAction.OnCreateFolderClicked -> uploadFolder(event.name)
            is MaterialAction.OnFolderClicked -> openFolder(event.folder)
            is MaterialAction.OnDeleteFileClicked -> deleteFile(event.fileId)
            is MaterialAction.OnDownloadFileClicked -> downloadFile(
                event.url, MimeType.getMimeTypeFromFileName(event.fileName)
            )

            is MaterialAction.OnFileClicked -> openFile(
                event.fileId, event.url, MimeType.getMimeTypeFromFileName(event.fileName)
            )

            is MaterialAction.OnShareLinkClicked -> shareLink(event.url)
            is MaterialAction.OnDeleteFolderClicked -> deleteFolder(event.folderId)
            is MaterialAction.OpenLink -> openLink(event.link)
            else -> Unit
        }
    }

    private fun handleUiState(result: Results<MaterialResponse.GetMaterialResponses, DataError.Network>) {
        when (result) {
            is Results.Failure -> {
                stopLoading()
                showError(result.error.userMessage)
            }

            Results.Loading -> {
                resetError()
                startLoading()
            }

            is Results.Success -> {
                resetError()
                stopLoading()
                updateData(result.data)
            }
        }
    }

    private fun openLink(url: String) {
        screenModelScope.launch {
            materialRepo.openLink(url)
        }
    }

    private fun shareLink(url: String) {
        screenModelScope.launch {
            materialRepo.shareLink(url)
        }
    }

    private fun openFile(fileId: String, url: String, mimeType: MimeType) {
        screenModelScope.launch {
            materialRepo.openFile(fileId, url, mimeType.mimeType)
        }
    }

    private fun startLoading() {
        uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun stopLoading() {
        uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun showError(error: String) {
        uiState.update {
            it.copy(
                error = error
            )
        }
    }

    private fun resetError() {
        uiState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun updateData(data: MaterialResponse.GetMaterialResponses) {
        uiState.update {
            it.copy(
                currentFiles = data.files, currentFolders = data.folders
            )
        }
    }
}