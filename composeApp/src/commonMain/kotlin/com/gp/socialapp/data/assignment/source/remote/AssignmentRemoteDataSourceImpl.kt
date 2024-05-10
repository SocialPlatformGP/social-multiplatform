package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.AssignmentError.*
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AssignmentRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : AssignmentRemoteDataSource {
    override suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String, CreateAssignment> {
        return try {
            val response = httpClient.post {
                endPoint("createAssignment")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val assignmentId = response.body<String>()
                success(assignmentId)
            } else {
                val error = response.body<CreateAssignment>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(CreateAssignment.SERVER_ERROR)
        }
    }

    override fun getAttachments(
        userId: String,
        assignmentId: String
    ): Flow<Result<UserAssignmentSubmission,GetAttachments>> = flow {
        try {
            val request = AssignmentRequest.GetUserAttachmentsRequest(userId, assignmentId)
            val response = httpClient.post {
                endPoint("getAttachments")
                setBody(request)
            }
            println("rea $response")
            if (response.status == HttpStatusCode.OK) {
                val assignmentAttachments = response.body<UserAssignmentSubmission>()
                emit(Result.Success(assignmentAttachments))
            } else {
                val error = response.body<GetAttachments>()
                error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(GetAttachments.SERVER_ERROR)
        }
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean, SubmitAssignment> {
        return try {
            val request = AssignmentRequest.SubmitRequest(assignmentId, userId, attachments)
            val response = httpClient.post {
                endPoint("submitAssignment")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(true)
            } else {
                val error = response.body<SubmitAssignment>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SubmitAssignment.SERVER_ERROR)
        }
    }


    override fun getAssignments(userId: String): Flow<Result<List<Assignment>,GetAssignments>> = flow {
        try {
            val response = httpClient.post {
                endPoint("getAssignments")
                setBody(userId)
            }
            if (response.status == HttpStatusCode.OK) {
                val assignments = response.body<List<Assignment>>()
                emit(Result.Success(assignments))
            } else {
                val error = response.body<GetAssignments>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GetAssignments.SERVER_ERROR)
        }
    }

    override suspend fun getAssignmentById(request: AssignmentRequest.GetAssignmentById): Result<Assignment,GetAssignment> {
        return try {
            val response = httpClient.post {
                endPoint("getAssignmentById")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val assignment = response.body<Assignment>()
                Result.Success(assignment)
            } else {
                val error = response.body<GetAssignment>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GetAssignment.SERVER_ERROR)
        }
    }

    override fun getSubmissions(request: AssignmentRequest.GetAssignmentSubmissions): Flow<Result<List<UserAssignmentSubmission>,GetSubmissions>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getSubmissions")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val submissions = response.body<List<UserAssignmentSubmission>>()
                    emit(Result.Success(submissions))
            } else {
                val error = response.body<GetSubmissions>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GetSubmissions.SERVER_ERROR)
        }
    }

    override suspend fun submitAssignmentSubmissionReview(request: AssignmentRequest.SubmitReview): Result<Boolean,SubmitReview> {
        return try {
            val response = httpClient.post {
                endPoint("submitReview")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(true)
            } else {
                val error = response.body<SubmitReview>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(SubmitReview.SERVER_ERROR)
        }
    }

    override suspend fun turnInAssignments(userAssignmentId: String): Result<Boolean,TurnInAssignments> {
        return try {
            val response = httpClient.post {
                endPoint("turnInAssignment")
                setBody(userAssignmentId)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(true)
            } else {
                val error = response.body<TurnInAssignments>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(TurnInAssignments.SERVER_ERROR)
        }
    }

    override suspend fun unSubmitAssignment(userAssignmentId: String): Result<Boolean,UnSubmitAssignment> {
        return try {
            val response = httpClient.post {
                endPoint("unSubmitAssignment")
                setBody(userAssignmentId)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(true)
            } else {
                val error = response.body<UnSubmitAssignment>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UnSubmitAssignment.SERVER_ERROR)
        }
    }
}