package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.assignment.model.AssignmentAttachment

@Composable
fun SubmissionAttachmentPreview(
    modifier: Modifier = Modifier,
    attachment: AssignmentAttachment?,
) {
    Box(
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = attachment?.name?:"No attachment selected",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}