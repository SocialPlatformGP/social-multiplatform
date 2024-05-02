package com.gp.socialapp.presentation.assignment.createassignment

import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateAssignmentUiState(
    val currentUser: User = User(),
    val communityId: String = "",
    val isCreated: Boolean = false,
    val assignmentId: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: Long = 0,
    val acceptLateSubmissions: Boolean = false,
    val maxPoints: Int = 10,
    val attachments: List<AssignmentAttachment> = emptyList()
)
