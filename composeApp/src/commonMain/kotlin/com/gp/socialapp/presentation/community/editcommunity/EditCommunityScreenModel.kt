package com.gp.socialapp.presentation.community.editcommunity

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.community.createcommunity.EmailDomain
import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditCommunityScreenModel(
    private val communityRepo: CommunityRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(EditCommunityUiState())
    val uiState = _uiState.asStateFlow()
    fun init(community: Community) {
        _uiState.update {
            it.copy(
                communityId = community.id,
                communityName = community.name,
                communityDescription = community.description,
                requireAdminApproval = community.isAdminApprovalRequired,
                allowAnyEmailDomain = community.allowAnyEmailDomain,
                allowedEmailDomains = community.allowedEmailDomains,
            )
        }
    }

    fun handleUiAction(action: EditCommunityUiAction) {
        when (action) {
            is EditCommunityUiAction.OnAddAllowedEmailDomain -> {
                addAllowedEmailDomain(action.domain)
            }

            is EditCommunityUiAction.OnAllowAnyEmailDomainChanged -> {
                setAllowAnyEmailDomain(action.value)
            }

            is EditCommunityUiAction.OnRemoveAllowedEmailDomain -> {
                removeAllowedEmailDomain(action.domain)
            }

            is EditCommunityUiAction.OnRequireAdminApprovalChanged -> {
                setRequireAdminApproval(action.value)
            }

            EditCommunityUiAction.OnSaveClicked -> {
                editCommunity()
            }

            is EditCommunityUiAction.OnUpdateCommunityDescription -> {
                setCommunityDescription(action.description)
            }

            is EditCommunityUiAction.OnUpdateCommunityName -> {
                setCommunityName(action.name)
            }

            else -> Unit
        }
    }

    private fun editCommunity() {
        println("EditCommunityScreenModel editCommunity uiState: ${uiState.value}")
        screenModelScope.launch(DispatcherIO) {
            communityRepo.editCommunity(
                Community(
                    id = uiState.value.communityId,
                    name = uiState.value.communityName,
                    description = uiState.value.communityDescription,
                    isAdminApprovalRequired = uiState.value.requireAdminApproval,
                    allowAnyEmailDomain = uiState.value.allowAnyEmailDomain,
                    allowedEmailDomains = uiState.value.allowedEmailDomains
                )
            ).let { result ->
                if (result.isSuccessful()) {
                    _uiState.update { oldState ->
                        oldState.copy(isFinished = true)
                    }
                } else {
                    // TODO Handle error
                }
            }
        }
    }

    private fun setCommunityName(name: String) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(communityName = name)
            }
        }
    }

    private fun setCommunityDescription(description: String) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(communityDescription = description)
            }
        }
    }

    private fun setRequireAdminApproval(value: Boolean) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(requireAdminApproval = value)
            }
        }
    }

    private fun removeAllowedEmailDomain(domain: EmailDomain) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(allowedEmailDomains = it.allowedEmailDomains - domain)
            }
        }
    }

    private fun setAllowAnyEmailDomain(value: Boolean) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(allowAnyEmailDomain = value)
            }
        }
    }

    private fun addAllowedEmailDomain(domain: EmailDomain) {
        screenModelScope.launch {
            _uiState.update {
                it.copy(allowedEmailDomains = it.allowedEmailDomains + domain)
            }
        }
    }

    fun clear() {
        _uiState.value = EditCommunityUiState()
    }
}