package com.gp.socialapp.presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val authRepo: AuthenticationRepository, private val userRepo: UserRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    fun init() {
        getUser()
    }

    private fun getUserCommunities() {
        screenModelScope.launch() {
            userRepo.getUserCommunities(uiState.value.user.id).collect {
                when (it) {
                    is Results.Failure -> {
                        resetLoading()
                        setError(it.error.userMessage)
                    }

                    Results.Loading -> {
                        resetError()
                        setLoading()
                    }

                    is Results.Success -> {
                        resetLoading()
                        resetError()
                        setCommunities(it.data)

                    }
                }
            }
        }
    }

    private fun getUser() {
        screenModelScope.launch() {
            authRepo.getSignedInUser().collect { result ->
                when (result) {
                    is Result.Error -> {
                        setError(result.message)
                    }

                    is Result.SuccessWithData -> {
                        setUser(result.data)
                        getUserCommunities()

                    }

                    else -> Unit
                }
            }
        }
    }
    ////////////////////////////////////////////

    private fun setUser(user: User) {
        _uiState.update {
            it.copy(user = user)
        }
    }

    private fun setCommunities(data: List<Community>) {
        _uiState.update {
            it.copy(communities = data)
        }
    }

    private fun setLoading() {
        _uiState.update {
            it.copy(loading = true)
        }
    }

    private fun resetError() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    private fun setError(userMessage: String) {
        _uiState.update {
            it.copy(error = userMessage)
        }
    }

    private fun resetLoading() {
        _uiState.update {
            it.copy(loading = false)
        }
    }

    fun communityLogout(id: String) {
        screenModelScope.launch {
            userRepo.communityLogout(
                uiState.value.user.id,
                id
            ).collect {
                when (it) {
                    is Results.Failure -> {
                        setError(it.error.userMessage)
                    }

                    Results.Loading -> {
                        setLoading()
                    }

                    is Results.Success -> {
                        resetLoading()
                        resetError()
                        setCommunities(it.data)
                    }
                }
            }
        }
    }

    fun joinCommunity(code: String) {
        screenModelScope.launch {
            userRepo.joinCommunity(uiState.value.user.id, code).collect {
                when (it) {
                    is Results.Failure -> {
                        setError(it.error.userMessage)
                        println(it.error.userMessage)
                        Napier.e(it.error.userMessage)
                    }

                    Results.Loading -> {
                        setLoading()
                    }

                    is Results.Success -> {
                        resetLoading()
                        resetError()
                        println(it.data.map { it.name })
                        setCommunities(it.data)
                    }
                }
            }
        }
    }

    fun userLogout() {
        screenModelScope.launch {
            authRepo.logout().onSuccess {
                _uiState.update {
                    it.copy(loggedOut = true)
                }
            }
        }
    }

    fun dispose() {
        _uiState.value = HomeUiState()
    }
}
