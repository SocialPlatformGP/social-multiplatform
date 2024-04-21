package com.gp.socialapp.presentation.material


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.data.material.repository.MaterialRepository
import com.gp.socialapp.presentation.material.components.MimeType
import com.gp.socialapp.util.DispatcherIO
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
                result.onSuccessWithData { data ->
                    uiState.update {
                        println("***********Data: $data***************")
                        it.copy(
                            currentFiles = data.files,
                            currentFolders = data.folders
                        )
                    }
                }
                result.onFailure {
                    println("***********Error: $it***************")
                }
            }
        }
    }

    fun uploadFolder(
        folderName: String
    ) {
        screenModelScope.launch {
            materialRepo.createFolder(
                name = folderName,
                path = state.value.currentFolder.path,
            ).collect { result ->
                result.onSuccessWithData { data ->
                    uiState.update {
                        it.copy(
                            currentFolders = data.folders,
                            currentFiles = data.files
                        )
                    }
                }

            }
        }
    }

    fun uploadFile(
        fileName: String,
        fileType: String,
        fileContent: ByteArray
    ) {
        screenModelScope.launch {
            materialRepo.createFile(
                name = fileName,
                type = fileType,
                path = state.value.currentFolder.path,
                content = fileContent
            ).collect { result ->
                result.onSuccessWithData { data ->
                    uiState.update {
                        it.copy(
                            currentFolders = data.folders,
                            currentFiles = data.files
                        )
                    }
                }

            }
        }
    }

    fun openFolder(folder: MaterialFolder) {
        val newFolder = Folder(
            path = folder.id,
            name = folder.name
        )
        uiState.update {
            it.copy(
                listOfPreviousFolder = state.value.listOfPreviousFolder.plus(state.value.currentFolder),
                currentFolder = newFolder,

                )
        }.apply {
            getMaterial()
        }
        println("currentFolder " + state.value.currentFolder.name + " " + state.value.currentFolder.path + " " + state.value.currentFolders.size + " " + state.value.currentFiles.size)
        println("openFolder " + state.value.listOfPreviousFolder.map { it.name })

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
        println("currentFolder " + state.value.currentFolder.name + " " + state.value.currentFolder.path + " " + state.value.currentFolders.size + " " + state.value.currentFiles.size)
        println("closeFolder " + state.value.listOfPreviousFolder.map { it.name })
    }

    fun deleteFile(fileId: String) {
        screenModelScope.launch {
            materialRepo.deleteFile(fileId, state.value.currentFolder.path).collect { result ->
                result.onSuccessWithData { data ->
                    uiState.update {
                        it.copy(
                            currentFolders = data.folders,
                            currentFiles = data.files
                        )
                    }
                }
            }

        }
    }

    fun deleteFolder(folderId: String) {
        screenModelScope.launch {
            materialRepo.deleteFolder(folderId).collect { result ->
                result.onSuccessWithData { data ->
                    uiState.update {
                        it.copy(
                            currentFolders = data.folders,
                            currentFiles = data.files
                        )
                    }
                }
            }
        }
    }

    fun downloadFile(url: String, mimeType: MimeType) {
        screenModelScope.launch(DispatcherIO) {
            materialRepo.downloadFile(url, mimeType.mimeType)
        }
    }

    fun handleUiEvent(event: MaterialAction) {
        when (event) {
            is MaterialAction.OnUploadFileClicked -> uploadFile(
                event.name,
                event.type,
                event.content
            )

            is MaterialAction.OnCreateFolderClicked -> uploadFolder(event.name)
            is MaterialAction.OnFolderClicked -> openFolder(event.folder)
            is MaterialAction.OnDeleteFileClicked -> deleteFile(event.fileId)
            is MaterialAction.OnDownloadFileClicked -> downloadFile(
                event.url,
                MimeType.getMimeTypeFromFileName(event.fileName)
            )

            is MaterialAction.OnFileClicked -> openFile(
                event.fileId,
                event.url,
                MimeType.getMimeTypeFromFileName(event.fileName)
            )

            is MaterialAction.OnShareLinkClicked -> shareLink(event.url)

            else -> Unit
        }
    }

    private fun shareLink(url: String) {
        screenModelScope.launch {
            materialRepo.shareLink(url)
        }
    }

    fun openFile(fileId: String, url: String, mimeType: MimeType) {
        screenModelScope.launch {
            materialRepo.openFile(fileId, url, mimeType.mimeType)
        }
    }
}