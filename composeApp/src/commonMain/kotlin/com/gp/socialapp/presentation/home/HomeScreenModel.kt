package com.gp.socialapp.presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
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
        getUserCommunities()
    }

    private fun getUserCommunities() {
        println("User in getCommunities: ${uiState.value.user.id}")
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
            when (val result = authRepo.getSignedInUser()) {
                is Result.Error -> {
                    setError(result.message)
                }

                is Result.SuccessWithData -> {
                    println("User in get: ${result.data.id}")
                    setUser(result.data)
                    getUserCommunities()

                }

                else -> Unit
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
            println("Communities: $data")
            it.copy(communities = data)
        }
    }

    private fun setLoading() {
        _uiState.update {
            println("Loading")
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
            println("Error: $userMessage")
            it.copy(error = userMessage)
        }
    }

    private fun resetLoading() {
        _uiState.update {
            it.copy(loading = false)
        }
    }
}
