package com.gp.socialapp.presentation.assignment.submitassignment

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.DataSuccess

data class SubmitAssignmentUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false,
    val assignment: Assignment = Assignment(),
    val newSubmission: List<AssignmentAttachment> = emptyList(),
    val oldSubmission: List<AssignmentAttachment> = emptyList(),
    val user :User = User()
)