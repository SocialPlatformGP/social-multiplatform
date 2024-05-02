package com.gp.socialapp.data.assignment.source.remote.model.request

import com.gp.socialapp.data.assignment.model.Assignment
import kotlinx.serialization.Serializable

sealed interface AssignmentRequest {
    @Serializable
    data class CreateRequest(val assignment: Assignment): AssignmentRequest
}