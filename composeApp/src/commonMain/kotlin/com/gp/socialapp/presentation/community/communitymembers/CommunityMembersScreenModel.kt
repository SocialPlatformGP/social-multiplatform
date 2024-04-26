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
import com.gp.socialapp.util.Results
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
            _uiState.update { it.copy(currentUserId = authRepo.getCurrentLocalUserId()) }
        }
    }

    private fun getCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.fetchCommunity(communityId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _uiState.update { it.copy(communityName = result.data.name) }
                        val memberIds = result.data.members.keys
                        getMembers(memberIds)
                        updateAdmins(result.data.members)
                    }

                    is Results.Failure -> {
                        //TODO: Handle error
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
                    is Results.Success -> {
                        updateMembers(it.data)
                    }

                    is Results.Failure -> {
                        //TODO: Handle error
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
        _uiState.update { it.copy(admins = admins.toList()) }
        if (admins.contains(_uiState.value.currentUserId)) {
            getRequests()
        }
    }

    private fun getRequests() {
        screenModelScope.launch(DispatcherIO) {
            communityRepo.fetchCommunityMembersRequests(_uiState.value.communityId).collect {
                when (it) {
                    is Results.Success -> {
                        updateRequests(it.data)
                    }

                    is Results.Failure -> {}
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
            is CommunityMembersUiAction.OnAcceptRequest -> acceptRequest(action.userId)
            is CommunityMembersUiAction.OnDeclineRequest -> declineRequest(action.userId)
            is CommunityMembersUiAction.OnUserClicked -> onUserClicked(action.userId)
        }
    }

    private fun acceptRequest(userId: String) {
        screenModelScope.launch {
            val result = communityRepo.acceptCommunityRequest(_uiState.value.communityId, userId)
            if (result is Results.Success) {
                getRequests()
            } else {
                //TODO: Handle error
            }
        }
    }

    private fun declineRequest(userId: String) {
        screenModelScope.launch {
            val result = communityRepo.declineCommunityRequest(_uiState.value.communityId, userId)
            if (result is Results.Success) {
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