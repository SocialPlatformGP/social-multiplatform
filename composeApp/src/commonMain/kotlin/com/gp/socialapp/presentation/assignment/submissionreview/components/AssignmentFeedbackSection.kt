package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AssignmentFeedbackSection(
    modifier: Modifier = Modifier,
    feedback: String,
    onFeedbackChanged: (String) -> Unit
) {
    var feedbackValue by remember { mutableStateOf(feedback) }
    Column (
        modifier = Modifier.then(modifier)
    ) {
        Text(text = "Feedback", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = feedbackValue,
            onValueChange = {
                feedbackValue = it
                onFeedbackChanged(it)
            },
            placeholder = { Text("Add Feedback") },
            modifier = Modifier.width(TextFieldDefaults.MinWidth)
        )
    }
}