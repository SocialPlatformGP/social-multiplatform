package com.gp.socialapp.presentation.home.container

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeContainerScreenModel(
    private val authRepo: AuthenticationRepository,
): ScreenModel {
    private val _uiState = MutableStateFlow(HomeContainerUiState())
    val uiState = _uiState.asStateFlow()
    fun init() {
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        /*TODO: Handle Error*/
                    }
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    fun logout() {
        screenModelScope.launch {
            authRepo.logout().onSuccess {
                _uiState.update {
                    it.copy(isLoggedOut = true)
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = HomeContainerUiState()
    }
}