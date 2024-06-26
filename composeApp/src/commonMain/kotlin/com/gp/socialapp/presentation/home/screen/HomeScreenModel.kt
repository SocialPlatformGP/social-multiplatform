package com.gp.socialapp.presentation.home.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
    private val communityRepo: CommunityRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    fun init() {
        getUser()
    }

    private fun getUserCommunities() {
        screenModelScope.launch {
            userRepo.getUserCommunities(uiState.value.user.id).collect {
                when (it) {
                    is Result.Error -> {
                        resetLoading()
                        setError(it.message.userMessage)
                    }

                    Result.Loading -> {
                        resetError()
                        setLoading()
                    }

                    is Result.Success -> {
                        resetLoading()
                        resetError()
                        setCommunities(it.data)

                    }
                }
            }
        }
    }

    private fun getUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        setError(result.message.userMessage)
                    }

                    is Result.Success -> {
                        setUser(result.data)
                        println(result.data)
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
                uiState.value.user.id, id
            ).collect {
                when (it) {
                    is Result.Error -> {
                        setError(it.message.userMessage)
                    }

                    Result.Loading -> {
                        setLoading()
                    }

                    is Result.Success -> {
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
                    is Result.Error -> {
                        setError(it.message.userMessage)
                        println(it.message.userMessage)
                        Napier.e(it.message.userMessage)
                    }

                    Result.Loading -> {
                        setLoading()
                    }

                    is Result.Success -> {
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
            val result = authRepo.logout()
            when (result) {
                is Result.Error -> {
                    setError(result.message.userMessage)
                }

                Result.Loading -> {
                    setLoading()
                }

                is Result.Success -> {
                    resetLoading()
                    resetError()
                    _uiState.update {
                        it.copy(loggedOut = true)
                    }
                }
            }
        }
    }


    fun dispose() {
        _uiState.value = HomeUiState()
    }

    fun deleteCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.deleteCommunity(communityId).let {
                when (it) {
                    is Result.Error -> {
                        setError(it.message.userMessage)
                    }

                    Result.Loading -> {
                        setLoading()
                    }

                    is Result.Success -> {
                        resetLoading()
                        resetError()
                        _uiState.update { state ->
                            state.copy(communities = state.communities.filter { it.id != communityId })
                        }
                    }
                }
            }
        }
    }
}
