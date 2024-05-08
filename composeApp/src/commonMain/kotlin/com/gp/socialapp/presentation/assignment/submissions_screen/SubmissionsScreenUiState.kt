package com.gp.socialapp.presentation.assignment.submissions_screen

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission

data class SubmissionsScreenUiState(
    val isLoading: Boolean = false,
    val submissions: List<UserAssignmentSubmission> = emptyList(),
    val error: String? = null,
    val assignment: Assignment = Assignment()
)