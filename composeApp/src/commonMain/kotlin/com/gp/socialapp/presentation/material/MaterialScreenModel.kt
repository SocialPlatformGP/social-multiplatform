package com.gp.socialapp.presentation.material


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.material.model.MaterialFolder
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.repository.MaterialRepository

import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getFullMimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.presentation.post.feed.FeedError
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MaterialScreenModel(
    private val materialRepo: MaterialRepository,
    private val authRepo: AuthenticationRepository,
    private val communityRepo: CommunityRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(MaterialUiState())
    val uiState = _uiState.asStateFlow()
    fun init(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                currentUser = result.data
                            )
                        }
                        getCurrentCommunity(communityId)
                        getMaterial()
                    }

                    is Result.Error -> {
                        Napier.e("Error: ${result.message}")
                    }

                    else -> Unit
                }
            }

        }
    }
    private fun getCurrentCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.fetchCommunity(communityId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                currentCommunity = result.data,
                                isAdmin =result.data.members[_uiState.value.currentUser.id] == true
                            )
                        }
                    }

                    is Result.Error -> {
                        println("Error: ${result.message.userMessage}")
                    }

                    else -> Unit
                }
            }
        }
    }

    fun getMaterial() {
        screenModelScope.launch {
            materialRepo.getMaterialAtPath(uiState.value.currentFolder.path).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }
            }
        }
    }


    private fun uploadFolder(
        folderName: String
    ) {
        screenModelScope.launch {
            materialRepo.createFolder(
                name = folderName,
                path = uiState.value.currentFolder.path,
                communityId = uiState.value.currentCommunity.id
            ).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }            }
        }
    }

    private fun uploadFile(
        fileName: String, fileType: String, fileContent: ByteArray
    ) {
        screenModelScope.launch {
            materialRepo.createFile(
                name = fileName,
                type = fileType,
                path = uiState.value.currentFolder.path,
                content = fileContent,
                communityId = uiState.value.currentCommunity.id
            ).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }            }
        }
    }

    private fun openFolder(folder: MaterialFolder) {
        val newFolder = Folder(
            path = folder.id, name = folder.name
        )
        _uiState.update {
            it.copy(
                listOfPreviousFolder = uiState.value.listOfPreviousFolder.plus(uiState.value.currentFolder),
                currentFolder = newFolder,

                )
        }.apply {
            getMaterial()
        }
    }

    fun closeFolder() {
        _uiState.update {
            it.copy(
                currentFolder = uiState.value.listOfPreviousFolder.last(),
            )
        }.also {
            _uiState.update {
                it.copy(
                    listOfPreviousFolder = uiState.value.listOfPreviousFolder.dropLast(1)
                )
            }.also {
                getMaterial()
            }
        }
    }

    private fun deleteFile(fileId: String) {
        screenModelScope.launch {
            materialRepo.deleteFile(fileId, uiState.value.currentFolder.path).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }            }
        }
    }

    private fun deleteFolder(folderId: String) {
        screenModelScope.launch {
            materialRepo.deleteFolder(folderId).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }            }
        }
    }

    private fun downloadFile(id: String, url: String, mimeType: MimeType) {
        screenModelScope.launch(DispatcherIO) {
            println("Downloading file screen model $url, $mimeType")
            val fullMimeType = getFullMimeType(mimeType)
            materialRepo.downloadFile(id, url, fullMimeType)
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
                event.id, event.url, getMimeTypeFromFileName(event.fileName)
            )

            is MaterialAction.OnRenameFolderClicked -> renameFolder(
                event.folder.id,
                event.folder.name
            )

            is MaterialAction.OnFileClicked -> openFile(
                event.fileId, event.url, getMimeTypeFromFileName(event.fileName)
            )
            is MaterialAction.OnDeleteFolderClicked -> deleteFolder(event.folderId)
            is MaterialAction.OpenLink -> openLink(event.link)
            else -> Unit
        }
    }

    private fun renameFolder(folderId: String, newName: String) {
        screenModelScope.launch {
            materialRepo.renameFolder(folderId, newName).collect { result ->
                when (result) {
                    is Result.Error -> {
                        stopLoading()
                        showError(result.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        startLoading()
                    }

                    is Result.Success -> {
                        resetError()
                        stopLoading()
                        updateData(result.data)
                    }
                }            }
        }
    }


    private fun openLink(url: String) {
        screenModelScope.launch {
            materialRepo.openLink(url)
        }
    }

    private fun openFile(fileId: String, url: String, mimeType: MimeType) {
        screenModelScope.launch {
            val fullMimeType = getFullMimeType(mimeType)
            materialRepo.openFile(fileId, url, fullMimeType)
        }
    }

    private fun startLoading() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun stopLoading() {
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun showError(error: String) {
        _uiState.update {
            it.copy(
                error = error
            )
        }
    }

    private fun resetError() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun updateData(data: MaterialResponse.GetMaterialResponses) {
        _uiState.update {
            it.copy(
                currentFiles = data.files.filter { it.communityId == uiState.value.currentCommunity.id },
                currentFolders = data.folders.filter { it.communityId == uiState.value.currentCommunity.id }
            )
        }
    }
}