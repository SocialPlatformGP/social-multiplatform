package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gp.socialapp.data.assignment.model.AssignmentAttachment

@Composable
actual fun PdfPreview(
    modifier: Modifier,
    attachment: AssignmentAttachment
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Gray).then(modifier)
    ) {
        Text(
            text= attachment.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}