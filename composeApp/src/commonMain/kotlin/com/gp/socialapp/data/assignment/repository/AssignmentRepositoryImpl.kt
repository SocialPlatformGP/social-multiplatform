package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.AssignmentRemoteDataSource
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.AssignmentError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AssignmentRepositoryImpl(
    private val remoteDataSource: AssignmentRemoteDataSource
): AssignmentRepository {
    override suspend fun createAssignment(assignment: Assignment): Result<String, AssignmentError.CreateAssignment> {
        val request = AssignmentRequest.CreateRequest(assignment)
        return remoteDataSource.createAssignment(request)
    }

    override fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission, AssignmentError.GetAttachments>> {
        return remoteDataSource.getAttachments(userId, assignmentId)
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean, AssignmentError.SubmitAssignment> {
        return remoteDataSource.submitAssignment(assignmentId, userId, attachments)
    }

    override suspend fun turnInAssignments(userAssignmentId: String): Result<Boolean, AssignmentError.TurnInAssignments> {
        return remoteDataSource.turnInAssignments(userAssignmentId)
    }

    override suspend fun unSubmitAssignment(userAssignmentId: String): Result<Boolean, AssignmentError.UnSubmitAssignment> {
        return remoteDataSource.unSubmitAssignment(userAssignmentId)
    }

    override fun getAssignments(userId: String): Flow<Result<List<Assignment>, AssignmentError.GetAssignments>> {
        return remoteDataSource.getAssignments(userId)
    }

    override suspend fun getAssignmentById(assignmentId: String): Result<Assignment, AssignmentError.GetAssignment> {
        val request = AssignmentRequest.GetAssignmentById(assignmentId)
        return remoteDataSource.getAssignmentById(request)

    }

    override fun getSubmissions(assignmentId: String): Flow<Result<List<UserAssignmentSubmission>, AssignmentError.GetSubmissions>> {
        val request = AssignmentRequest.GetAssignmentSubmissions(assignmentId)
        return remoteDataSource.getSubmissions(request)
    }

    override suspend fun submitAssignmentSubmissionReview(
        submissionId: String,
        grade: Int,
        feedback: String
    ): Result<Boolean, AssignmentError.SubmitReview> {
        val request = AssignmentRequest.SubmitReview(submissionId, grade, feedback)
        return remoteDataSource.submitAssignmentSubmissionReview(request)
    }
}