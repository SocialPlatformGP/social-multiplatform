package com.gp.socialapp.presentation.assignment.homeassignment

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssignmentHomeScreenModel(
    private val assignmentRepo: AssignmentRepository, private val authenticationRepo: AuthenticationRepository

) : ScreenModel {
    private val _uiState = MutableStateFlow(AssignmentHomeUiState())
    val uiState = _uiState.asStateFlow()
    fun onInit(communityId: String) {
        getCurrentUser(communityId)
    }

    private fun getCurrentUser(communityId: String) {
        screenModelScope.launch {
            when (val user = authenticationRepo.getSignedInUser()) {
                is Result.Error -> println(user.message)
                Result.Loading -> {}
                is Result.SuccessWithData -> {
                    _uiState.update { it.copy(currentUser = user.data) }
                    getAssignments(user.data.id, communityId)
                }

                else -> Unit
            }
        }
    }

    private fun getAssignments(id: String, communityId: String) {
        screenModelScope.launch {
            assignmentRepo.getAssignments(id).collect { result ->
                when (result) {
                    Result.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Result.SuccessWithData -> {
                        _uiState.update { state ->
                            val data =
                                if (communityId.isEmpty()) result.data else result.data.filter { communityId == it.communityId }
                            state.copy(assignments = data, isLoading = false)
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }

                    else -> {}
                }
            }

        }
    }

}