package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.util.AssignmentError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {
    suspend fun createAssignment(
        assignment: Assignment
    ): Result<String, AssignmentError>

    fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission,AssignmentError>>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean, AssignmentError>

    fun getAssignments(userId: String): Flow<Result<List<Assignment>,AssignmentError>>

    suspend fun getAssignmentById(assignmentId: String): Result<Assignment, AssignmentError>
    fun getSubmissions(assignmentId: String): Flow<Result<List<UserAssignmentSubmission>,AssignmentError>>
    suspend fun submitAssignmentSubmissionReview(
        submissionId: String,
        grade: Int,
        feedback: String,
    ): Result<Boolean, AssignmentError>

    suspend fun turnInAssignments(userAssignmentId: String):Result<Boolean,AssignmentError>
    suspend fun unSubmitAssignment(userAssignmentId: String): Result<Boolean,AssignmentError>
}