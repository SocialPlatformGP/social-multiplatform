package com.gp.socialapp.presentation.community.createcommunity

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCommunityScreenModel(
    private val communityRepo: CommunityRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreateCommunityUiState())
    private lateinit var currentUserId: String
    val uiState = _uiState.asStateFlow()

    fun init() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().collect { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        currentUserId = result.data.id
                    }

                    is Result.Error -> {
                        Napier.e("Error getting signed in user")
                    }

                    else -> Unit
                }

            }
        }
    }

    fun handleUiAction(action: CreateCommunityAction) {
        when (action) {
            is CreateCommunityAction.OnAddAllowedEmailDomain -> {
                addEmailDomain(action.domain)
            }

            is CreateCommunityAction.OnAllowAnyEmailDomainChanged -> {
                setAllowAnyEmailDomain(action.value)
            }

            CreateCommunityAction.OnCreateCommunityClicked -> {
                createCommunity()
            }

            is CreateCommunityAction.OnRemoveAllowedEmailDomain -> {
                removeEmailDomain(action.domain)
            }

            is CreateCommunityAction.OnRequireAdminApprovalChanged -> {
                setRequireAdminApproval(action.value)
            }

            is CreateCommunityAction.OnUpdateCommunityDescription -> {
                setCommunityDescription(action.description)
            }

            is CreateCommunityAction.OnUpdateCommunityName -> {
                setCommunityName(action.name)
            }

            else -> Unit
        }
    }

    private fun setCommunityName(name: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(communityName = name) }
        }
    }

    private fun setCommunityDescription(description: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(communityDescription = description) }
        }
    }

    private fun setRequireAdminApproval(value: Boolean) {
        screenModelScope.launch {
            _uiState.update { it.copy(requireAdminApproval = value) }
        }
    }

    private fun createCommunity() {
        screenModelScope.launch {
            with(_uiState.value) {
                val community = Community(
                    name = communityName,
                    description = communityDescription,
                    isAdminApprovalRequired = requireAdminApproval,
                    allowAnyEmailDomain = allowAnyEmailDomain,
                    allowedEmailDomains = allowedEmailDomains,
                )
                communityRepo.createCommunity(community, currentUserId).let { result ->
                    if (result.isSuccessful()) {
                        setCreatedCommunity((result as Results.Success).data)
                    } else {
                        //TODO: Handle error
                    }
                }
            }
        }
    }

    private fun setCreatedCommunity(data: Community) {
        screenModelScope.launch {
            _uiState.update { it.copy(createdCommunity = data) }
        }
    }

    private fun setAllowAnyEmailDomain(value: Boolean) {
        screenModelScope.launch {
            _uiState.update { it.copy(allowAnyEmailDomain = value) }
        }
    }

    private fun addEmailDomain(domain: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(allowedEmailDomains = it.allowedEmailDomains + domain) }
        }
    }

    private fun removeEmailDomain(domain: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(allowedEmailDomains = it.allowedEmailDomains - domain) }
        }
    }

    fun dispose() {
        _uiState.value = CreateCommunityUiState()
    }
}