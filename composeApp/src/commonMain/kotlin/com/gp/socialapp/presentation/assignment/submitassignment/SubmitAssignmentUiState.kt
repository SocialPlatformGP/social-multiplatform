package com.gp.socialapp.presentation.assignment.submitassignment

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.auth.source.remote.model.User

data class SubmitAssignmentUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false,
    val assignment: Assignment = Assignment(),
    val newSubmission: UserAssignmentSubmission = UserAssignmentSubmission(),
    val oldSubmission: UserAssignmentSubmission = UserAssignmentSubmission(),
    val user: User = User()
)