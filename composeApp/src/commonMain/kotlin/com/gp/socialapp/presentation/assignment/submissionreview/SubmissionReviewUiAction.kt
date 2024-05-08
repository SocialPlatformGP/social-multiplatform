package com.gp.socialapp.presentation.assignment.submissionreview

import com.gp.socialapp.data.assignment.model.AssignmentAttachment

sealed interface SubmissionReviewUiAction {
    data object BackPressed : SubmissionReviewUiAction
    data object SubmitReview : SubmissionReviewUiAction
    data class ViewPrevious(val previousId: String) : SubmissionReviewUiAction
    data class ViewNext(val nextId: String) : SubmissionReviewUiAction
    data class AttachmentClicked(val attachmentId: String) : SubmissionReviewUiAction

    data class ViewSubmission(val submissionId: String): SubmissionReviewUiAction
    data class GradeChanged(val grade: String) : SubmissionReviewUiAction
    data class FeedbackChanged(val feedback: String) : SubmissionReviewUiAction
    data class DownloadAttachment(val attachment: AssignmentAttachment) : SubmissionReviewUiAction
}