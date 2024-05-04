package com.gp.socialapp.data.assignment.source.remote.model.request

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import kotlinx.serialization.Serializable

sealed interface AssignmentRequest {
    @Serializable
    data class CreateRequest(val assignment: Assignment): AssignmentRequest
    @Serializable
    data class GetUserAttachmentsRequest(
        val userId: String,
        val assignmentId: String
    ): AssignmentRequest
    @Serializable
    data class SubmitRequest(
        val assignmentId: String,
        val userId: String,
        val attachments: List<AssignmentAttachment>
    ) : AssignmentRequest
}