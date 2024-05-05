package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AssignmentRemoteDataSource {
    suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String>
    fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission>>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean>

    fun getAssignments(userId: String): Flow<Result<List<Assignment>>>
    suspend fun getAssignmentById(request: AssignmentRequest.GetAssignmentById): Result<Assignment>
    fun getSubmissions(request: AssignmentRequest.GetAssignmentSubmissions): Flow<Result<List<UserAssignmentSubmission>>>
    suspend fun submitAssignmentSubmissionReview(
        request: AssignmentRequest.SubmitReview
    ): Result<Boolean>
}