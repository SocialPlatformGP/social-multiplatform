package com.gp.socialapp.presentation.material


import com.eygraber.uri.Uri
import com.gp.material.model.MaterialItem
import com.gp.material.repository.MaterialRepository
import com.gp.socialapp.util.Result
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MaterialViewModel(private val materialRepo: MaterialRepository) : ViewModel() {
    private val _actionResult = MutableStateFlow<Result<String>>(Result.Idle)
    val actionResult = _actionResult.asStateFlow()

    private val _currentPath = MutableStateFlow("materials")
    val currentPath = _currentPath.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _items = MutableStateFlow<List<MaterialItem>>(emptyList())
    val items = _items.asStateFlow()

    init {
        viewModelScope.launch {
            _currentPath.collect {
                fetchDataFromFirebaseStorage()
            }
        }
    }

    private fun fetchDataFromFirebaseStorage() {
        viewModelScope.launch {
            materialRepo.getListOfFiles(_currentPath.value).collect {
                when (it) {
                    is Result.SuccessWithData -> {
                        _items.value = it.data
                        _isLoading.value = false
                        _actionResult.value = Result.Idle
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _actionResult.value = Result.Error("Failed to fetch data from remote host")
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    else -> {

                    }
                }
            }
        }
    }

    fun openFolder(path: String) {
        _currentPath.value = path
    }

    fun getCurrentPath(): String {
        return _currentPath.value
    }

    fun uploadFile(fileUri: Uri) {
        viewModelScope.launch {
            materialRepo.uploadFile(_currentPath.value, fileUri).collect {
                when (it) {
                    is Result.Success -> {
                        _isLoading.value = false
                        _actionResult.value =
                            Result.SuccessWithData("File has been uploaded successfully")
                        fetchDataFromFirebaseStorage()
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _actionResult.value = Result.Error("Failed to upload file")
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    else -> {}
                }
            }
        }
    }

    fun uploadFolder(name: String) {
        viewModelScope.launch {
            materialRepo.uploadFolder(_currentPath.value, name).collect {
                when (it) {
                    is Result.Success -> {
                        _isLoading.value = false
                        _actionResult.value =
                            Result.SuccessWithData("Folder has been created successfully")
                        fetchDataFromFirebaseStorage()
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _actionResult.value = Result.Error("Failed to create folder")
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteFolder(currentPath: String) {
        viewModelScope.launch {
            materialRepo.deleteFolder(currentPath).collect {
                when (it) {
                    is Result.Success -> {
                        _isLoading.value = false
                        _actionResult.value =
                            Result.SuccessWithData("Folder has been deleted successfully")
                        fetchDataFromFirebaseStorage()
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _actionResult.value = Result.Error("Failed to delete folder")
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteFile(fileLocation: String) {
        viewModelScope.launch {
            materialRepo.deleteFile(fileLocation).collect {
                when (it) {
                    is Result.Success -> {
                        _isLoading.value = false
                        _actionResult.value =
                            Result.SuccessWithData("File has been deleted successfully")
                        fetchDataFromFirebaseStorage()
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _actionResult.value = Result.Error("Failed to delete file")
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    else -> {

                    }
                }
            }
        }
    }

    fun goBack(): Boolean {
        val lastSlashIndex = _currentPath.value.lastIndexOf("/")
        return if (lastSlashIndex != 0) {
            _currentPath.value = _currentPath.value.substring(0, lastSlashIndex)
            true
        } else {
            _currentPath.value = "materials"
            false
        }
    }

    fun getCurrentFolderName(path: String): String {
        val lastSlashIndex = path.lastIndexOf("/")
        return if (lastSlashIndex != 0 && lastSlashIndex != -1) {
            path.substring(startIndex = lastSlashIndex + 1)
        } else {
            "Home"
        }
    }
}
