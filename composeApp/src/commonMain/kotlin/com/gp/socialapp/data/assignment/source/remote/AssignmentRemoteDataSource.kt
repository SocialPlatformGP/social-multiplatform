package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.AssignmentError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AssignmentRemoteDataSource {
    suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String, AssignmentError.CreateAssignment>
    fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission, AssignmentError.GetAttachments>>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean, AssignmentError.SubmitAssignment>

    fun getAssignments(userId: String): Flow<Result<List<Assignment>, AssignmentError.GetAssignments>>
    suspend fun getAssignmentById(request: AssignmentRequest.GetAssignmentById): Result<Assignment, AssignmentError.GetAssignment>
    fun getSubmissions(request: AssignmentRequest.GetAssignmentSubmissions): Flow<Result<List<UserAssignmentSubmission>, AssignmentError.GetSubmissions>>
    suspend fun submitAssignmentSubmissionReview(
        request: AssignmentRequest.SubmitReview
    ): Result<Boolean, AssignmentError.SubmitReview>

    suspend fun turnInAssignments(userAssignmentId: String): Result<Boolean, AssignmentError.TurnInAssignments>
    suspend fun unSubmitAssignment(userAssignmentId: String): Result<Boolean, AssignmentError.UnSubmitAssignment>
}