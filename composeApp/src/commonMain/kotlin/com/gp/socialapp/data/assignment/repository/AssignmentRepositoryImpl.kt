package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.AssignmentRemoteDataSource
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AssignmentRepositoryImpl(
    private val remoteDataSource: AssignmentRemoteDataSource
): AssignmentRepository {
    override suspend fun createAssignment(assignment: Assignment): Result<String> {
        val request = AssignmentRequest.CreateRequest(assignment)
        return remoteDataSource.createAssignment(request)
    }

    override fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission>> {
        return remoteDataSource.getAttachments(userId, assignmentId)
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean> {
        return remoteDataSource.submitAssignment(assignmentId, userId, attachments)
    }

    override fun getAssignments(userId: String): Flow<Result<List<Assignment>>> {
        return remoteDataSource.getAssignments(userId)
    }
}