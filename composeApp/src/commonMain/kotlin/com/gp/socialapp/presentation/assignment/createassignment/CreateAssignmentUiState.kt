package com.gp.socialapp.presentation.assignment.createassignment

import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class CreateAssignmentUiState(
    val currentUser: User = User(),
    val communityId: String = "",
    val isCreated: Boolean = false,
    val assignmentId: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds() + 25 * 60 * 60 * 1000,
    val acceptLateSubmissions: Boolean = false,
    val maxPoints: Int = 10,
    val attachments: List<AssignmentAttachment> = emptyList()
)
