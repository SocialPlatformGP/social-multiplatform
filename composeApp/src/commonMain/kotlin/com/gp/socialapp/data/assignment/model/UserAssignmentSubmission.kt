package com.gp.socialapp.data.assignment.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAssignmentSubmission(
    val id : String = "",
    val assignmentId: String="",
    val userId: String="",
    val attachments: List<AssignmentAttachment> = emptyList(),
    val submittedAt: Long = 0,
    val grade: Int = 0,
    val isReviewed: Boolean = false,
    val isAccepted: Boolean = false,
    val isReturned: Boolean = false,
    val isTurnedIn: Boolean = false
    )