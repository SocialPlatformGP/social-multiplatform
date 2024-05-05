package com.gp.socialapp.presentation.assignment.submitassignment

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubmitAssignmentScreenModel(
    private val assignmentRepository: AssignmentRepository,
    private val authRepository: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(SubmitAssignmentUiState())
    val uiState = _uiState.asStateFlow()
    fun init(assignment: Assignment) {
        screenModelScope.launch {
            when (val user = authRepository.getSignedInUser()) {
                is Result.Error -> {
                    println("Error${user.message}")
                }

                Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }

                is Result.SuccessWithData -> {
                    _uiState.value = _uiState.value.copy(assignment = assignment, user = user.data)
                    getOldSubmission(assignment)
                }

                else -> {}
            }
        }
    }
    private fun getOldSubmission(assignment: Assignment) {
        screenModelScope.launch {
            val userId = uiState.value.user.id
            val assignmentId = assignment.id
            assignmentRepository.getAttachments(userId, assignmentId).collect { result ->
                println("resulto ${result}")
                when (result) {
                    is Result.Error -> {
                        println("Error${result.message}")
                    }

                    Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Result.SuccessWithData -> {
                        _uiState.value =
                            _uiState.value.copy(oldSubmission = result.data, isLoading = false)
                    }

                    else -> {}
                }

            }
        }
    }

    private fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ) {
        screenModelScope.launch {
            when (val result = assignmentRepository.submitAssignment(assignmentId, userId, attachments)) {
                is Result.Error -> {
                    println("Error${result.message}")
                }

                Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }

                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, newSubmission = UserAssignmentSubmission())
                }

                else -> {}
            }
        }
    }

    fun handleAction(action: SubmitAssignmentUiAction) {
        when (action) {
            is SubmitAssignmentUiAction.OnUploadAttachment -> {
                _uiState.update {
                    it.copy(newSubmission = it.newSubmission.copy(attachments = listOf( action.attach)))
                }
                with(uiState.value) {
                    submitAssignment(
                        assignmentId = assignment.id,
                        userId = user.id,
                        attachments = newSubmission.attachments
                    )
                    getOldSubmission(assignment)
                }

            }

            else -> {}
        }
    }
}