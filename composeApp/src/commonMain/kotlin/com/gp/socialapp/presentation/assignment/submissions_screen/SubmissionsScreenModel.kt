package com.gp.socialapp.presentation.assignment.submissions_screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubmissionsScreenModel(
private val assignmentRepo: AssignmentRepository
):ScreenModel {
    private val _uiState = MutableStateFlow(SubmissionsScreenUiState())
    val uiState = _uiState.asStateFlow()
    fun init(assignment: Assignment){
        getAssignmentSubmissions(assignmentId = assignment.id)
    }

    private fun getAssignmentSubmissions(assignmentId: String) {
        screenModelScope.launch {
            assignmentRepo.getSubmissions(assignmentId).collect{result->
                when(result){
                    is Result.Error -> {
                        println(result.message)
                    }
                    Result.Loading -> {}
                    is Result.SuccessWithData -> {
                        _uiState.value = uiState.value.copy(submissions = result.data)
                    }
                    else->Unit
                }

            }
        }

    }
}