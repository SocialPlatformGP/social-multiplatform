package com.gp.socialapp.presentation.assignment.homeassignment

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssignmentHomeScreenModel (
    val assignmentRepo: AssignmentRepository

):ScreenModel {
    private val _uiState = MutableStateFlow(AssignmentHomeUiState())
    val uiState = _uiState.asStateFlow()
    fun onInit(communityId: String) {
        screenModelScope.launch {
            assignmentRepo.getAssignments(communityId).collect { result ->
                when(result){
                    Result.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(assignments = result.data, isLoading = false)
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }
                    else->{}
                }
            }

        }



    }
    override fun onDispose() {
        super.onDispose()
    }
}