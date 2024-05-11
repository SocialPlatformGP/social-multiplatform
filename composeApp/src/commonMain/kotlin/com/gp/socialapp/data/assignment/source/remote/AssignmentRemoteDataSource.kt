package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.AssignmentError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AssignmentRemoteDataSource {
    suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String, AssignmentError>
    fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission, AssignmentError>>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean, AssignmentError>

    fun getAssignments(userId: String): Flow<Result<List<Assignment>, AssignmentError>>
    suspend fun getAssignmentById(request: AssignmentRequest.GetAssignmentById): Result<Assignment, AssignmentError>
    fun getSubmissions(request: AssignmentRequest.GetAssignmentSubmissions): Flow<Result<List<UserAssignmentSubmission>, AssignmentError>>
    suspend fun submitAssignmentSubmissionReview(
        request: AssignmentRequest.SubmitReview
    ): Result<Boolean, AssignmentError>

    suspend fun turnInAssignments(userAssignmentId: String): Result<Boolean, AssignmentError>
    suspend fun unSubmitAssignment(userAssignmentId: String): Result<Boolean, AssignmentError>
}