package com.gp.socialapp.presentation.community.communitymembers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.community.source.remote.model.UserId
import com.gp.socialapp.data.community.source.remote.model.isAdmin
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityMembersScreenModel(
    private val authRepo: AuthenticationRepository,
    private val communityRepo: CommunityRepository,
    private val userRepo: UserRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(CommunityMembersUiState())
    val uiState = _uiState.asStateFlow()
    fun onInit(communityId: String) {
        getUserId()
        getCommunity(communityId)

    }

    private fun getUserId() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { it.copy(currentUserId = result.data.id) }
                    }

                    is Result.Error -> {
                        println("Error: ${result.message}")
                    }

                    else -> Unit
                }
            }

        }
    }

    private fun getCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.fetchCommunity(communityId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                communityName = result.data.name,
                                communityId = communityId
                            )
                        }
                        val memberIds = result.data.members.keys
                        getMembers(memberIds)
                        updateAdmins(result.data.members)
                    }

                    is Result.Error -> {
                        println("Error: ${result.message.userMessage}")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getMembers(memberIds: Set<UserId>) {
        screenModelScope.launch(DispatcherIO) {
            userRepo.getUsersByIds(memberIds.toList()).collect {
                when (it) {
                    is Result.Success -> {
                        updateMembers(it.data)
                        println("Members: ${it.data}")
                        Napier.d("Members: ${it.data}")
                    }

                    is Result.Error -> {
                        println("Error: ${it.message.userMessage}")
                        Napier.e("Error getting members")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun updateMembers(data: List<User>) {
        _uiState.update { it.copy(members = data) }
    }

    private fun updateAdmins(members: Map<UserId, isAdmin>) {
        val admins = members.filter { it.value }.keys
        Napier.d("Admins: $admins")
        _uiState.update { it.copy(admins = admins.toList()) }
        Napier.d("Current ${_uiState.value.currentUserId}")
        Napier.d("Current ${admins.contains(_uiState.value.currentUserId)}")
        if (admins.contains(_uiState.value.currentUserId)) {
            getRequests()
        }
    }

    private fun getRequests() {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.fetchCommunityMembersRequests(_uiState.value.communityId).collect {
                when (it) {
                    is Result.Success -> {
                        updateRequests(it.data)
                        Napier.d("Requests: ${it.data}")
                    }

                    is Result.Error -> {
                        println("Error: ${it.message.userMessage}")
                        Napier.e("Error getting requests")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun updateRequests(data: List<CommunityMemberRequest>) {
        _uiState.update { it.copy(requests = data) }
    }

    override fun onDispose() {
        _uiState.value = CommunityMembersUiState()
    }

    fun handleUiAction(action: CommunityMembersUiAction) {
        when (action) {
            is CommunityMembersUiAction.OnAcceptRequest -> acceptRequest(action.requestId)
            is CommunityMembersUiAction.OnDeclineRequest -> declineRequest(action.requestId)
            is CommunityMembersUiAction.OnUserClicked -> onUserClicked(action.userId)
            else -> Unit
        }
    }

    private fun acceptRequest(requestId: String) {
        screenModelScope.launch {
            val result = communityRepo.acceptCommunityRequest(requestId)
            if (result is Result.Success) {
                getRequests()
            } else {
                //TODO: Handle error
            }
        }
    }

    private fun declineRequest(requestId: String) {
        screenModelScope.launch {
            val result = communityRepo.declineCommunityRequest(requestId)
            if (result is Result.Success) {
                getRequests()
            } else {
                //TODO: Handle error
            }
        }
    }

    private fun onUserClicked(userId: String) {
//        TODO("Not yet implemented")
    }
}