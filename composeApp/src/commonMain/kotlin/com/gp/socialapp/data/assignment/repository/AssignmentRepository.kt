package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {
    suspend fun createAssignment(
        assignment: Assignment
    ): Result<String>

    fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission>>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean>

    fun getAssignments(userId: String): Flow<Result<List<Assignment>>>
}