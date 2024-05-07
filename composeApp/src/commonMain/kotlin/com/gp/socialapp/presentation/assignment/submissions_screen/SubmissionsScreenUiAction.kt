package com.gp.socialapp.presentation.assignment.submissions_screen

import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission

sealed interface SubmissionsScreenUiAction {
    data class SubmissionClick(val submission: UserAssignmentSubmission) : SubmissionsScreenUiAction
}