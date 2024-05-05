package com.gp.socialapp.presentation.assignment.submissionreview

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission

data class SubmissionReviewUiState(
    val currentAssignment: Assignment = Assignment(),
    val currentSubmissionId: String = "",
    val submissions: List<UserAssignmentSubmission> = emptyList(),
    val grade: Int = -1,
    val feedback: String = "",
    val currentPreviewedAttachmentId: String = "",
)