package com.gp.socialapp.presentation.assignment.createassignment

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAssignmentScreenModel(
    private val authRepo: AuthenticationRepository, private val assignmentRepo: AssignmentRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreateAssignmentUiState())

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
                        println("Error getting signed in user")
                        Napier.d("Error getting signed in user")
                    }

                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = CreateAssignmentUiState()
    }

    fun handleUiAction(action: CreateAssignmentUiAction) {
        when (action) {
            is CreateAssignmentUiAction.AcceptLateSubmissionsChanged -> {
                updateAcceptLateSubmissions(action.acceptLateSubmissions)
            }

            is CreateAssignmentUiAction.AttachmentAdded -> {
                addAttachment(action.name, action.type, action.size, action.byteArray)
            }

            is CreateAssignmentUiAction.AttachmentRemoved -> {
                removeAttachment(action.attachment)
            }

            CreateAssignmentUiAction.CreateAssignment -> {
                createAssignment()
            }

            is CreateAssignmentUiAction.DescriptionChanged -> {
                updateDescription(action.description)
            }

            is CreateAssignmentUiAction.DueDateChanged -> {
                updateDueDate(action.dueDate)
            }

            is CreateAssignmentUiAction.MaxPointsChanged -> {
                updateMaxPoints(action.maxPoints)
            }

            is CreateAssignmentUiAction.TitleChanged -> {
                updateTitle(action.title)
            }

            else -> Unit
        }
    }

    private fun createAssignment() {
        screenModelScope.launch(DispatcherIO) {
            val assignment = Assignment(
                title = _uiState.value.title,
                description = _uiState.value.description,
                dueDate = _uiState.value.dueDate,
                acceptLateSubmissions = _uiState.value.acceptLateSubmissions,
                maxPoints = _uiState.value.maxPoints,
                attachments = _uiState.value.attachments,
                creatorId = _uiState.value.currentUser.id,
                communityId = _uiState.value.communityId
            )
            assignmentRepo.createAssignment(assignment).let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(isCreated = true, assignmentId = result.data)
                        }
                    }
                    is Result.Error -> {
                        println(result.message)
                    }

                    Result.Loading -> {
                        println("Loading")
                    }
                }
            }
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    private fun updateMaxPoints(maxPoints: Int) {
        _uiState.update {
            it.copy(maxPoints = maxPoints)
        }
    }

    private fun updateDueDate(dueDate: Long) {
        _uiState.update {
            println("Due date: $dueDate")
            it.copy(dueDate = dueDate)
        }
    }

    private fun updateDescription(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }

    private fun removeAttachment(attachment: AssignmentAttachment) {
        _uiState.update {
            it.copy(attachments = it.attachments - attachment)
        }
    }

    private fun addAttachment(name: String, type: String, size: Long, byteArray: ByteArray) {
        val attachment = AssignmentAttachment(
            name = name, type = type, size = size, byteArray = byteArray
        )
        _uiState.update {
            it.copy(attachments = it.attachments + attachment)
        }
    }

    private fun updateAcceptLateSubmissions(acceptLateSubmissions: Boolean) {
        _uiState.update {
            it.copy(acceptLateSubmissions = acceptLateSubmissions)
        }
    }
}