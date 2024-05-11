package com.gp.socialapp.presentation.community.communityhome

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityHomeContainerScreenModel(
    private val userRepo: UserRepository,
    private val authRepo: AuthenticationRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(CommunityHomeUiState())
    val uiState = _uiState.asStateFlow()
    fun init(communityId: String) {
        _uiState.update {
            it.copy(communityId = communityId)
        }
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        /*TODO: Handle Error*/
                    }

                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                        getUserCommunities()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getUserCommunities() {
        screenModelScope.launch(DispatcherIO) {
            userRepo.getUserCommunities(uiState.value.currentUser.id).collect {
                when (it) {
                    is Result.Error -> {
                        println(it.message)
                    }

                    Result.Loading -> {
                        println("Loading")
                    }

                    is Result.Success -> {
                        setCommunities(it.data)
                    }
                }
            }
        }
    }

    private fun setCommunities(data: List<Community>) {
        _uiState.update {
            it.copy(userCommunities = data)
        }
    }

    fun dispose() {

    }

    fun logout() {
        screenModelScope.launch {
            val resut = authRepo.logout()
            when (resut) {
                is Result.Error -> {

                }

                Result.Loading -> TODO()
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoggedOut = true)
                    }
                }
            }

        }
    }
}