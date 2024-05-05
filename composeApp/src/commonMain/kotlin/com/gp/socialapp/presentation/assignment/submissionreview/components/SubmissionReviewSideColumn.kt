package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.presentation.assignment.createassignment.components.AssignmentGradeSection
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.util.LocalDateTimeUtil.getSubmissionFormattedDate

@Composable
fun SubmissionReviewSideColumn(
    modifier: Modifier = Modifier,
    currentSubmission: UserAssignmentSubmission,
    currentPreviewedAttachmentId: String,
    maxAssignmentGrade: Int,
    onAttachmentClicked: (String) -> Unit,
    onGradeChanged: (String) -> Unit,
    onFeedbackChanged: (String) -> Unit
) {
    Column (
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = "Submitted by: ${currentSubmission.userName}")
        //User name and Turned in status
        Text(text = "Submitted at: ${currentSubmission.submittedAt.getSubmissionFormattedDate()}", style = MaterialTheme.typography.bodySmall)
        //AttachmentList
        AssignmentAttachmentsList(
            selectedAttachmentId = currentPreviewedAttachmentId,
            attachments = currentSubmission.attachments,
            onAttachmentClicked = onAttachmentClicked
        )
        //Grade section
        AssignmentGradeSection(
            grade = currentSubmission.grade,
            maxGrade = maxAssignmentGrade,
            onGradeChanged = onGradeChanged
        )
        //Feedback section
        AssignmentFeedbackSection(
            feedback = currentSubmission.feedback,
            onFeedbackChanged = onFeedbackChanged
        )
    }
}