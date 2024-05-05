package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.data.post.util.endPoint
import io.ktor.client.HttpClient
import com.gp.socialapp.util.Result
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AssignmentRemoteDataSourceImpl(
    private val httpClient: HttpClient,
): AssignmentRemoteDataSource {
    override suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String> {
        return try{
            val response = httpClient.post {
                endPoint("createAssignment")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                val assignmentId = response.body<String>()
                Result.SuccessWithData(assignmentId)
            } else {
                Result.Error("Error creating assignment: ${response.status}")
            }
        } catch (e: Exception) {
            Result.Error("Error creating assignment: ${e.message}")
        }
    }

    override fun getAttachments(userId: String, assignmentId: String): Flow<Result<UserAssignmentSubmission>> = flow {
        try{
            val request = AssignmentRequest.GetUserAttachmentsRequest(userId, assignmentId)
            val response = httpClient.post {
                endPoint("getAttachments")
                setBody(request)
            }
            println("rea $response")
            if(response.status == HttpStatusCode.OK){
                val assignmentAttachments = response.body<UserAssignmentSubmission>()
                emit(Result.SuccessWithData(assignmentAttachments))
            } else {
                emit(Result.Error("Error getting attachments: ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Error getting attachments: ${e.message}"))
            e.printStackTrace()
        }
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Result<Boolean> {
        return try{
            val request = AssignmentRequest.SubmitRequest(assignmentId, userId, attachments)
            val response = httpClient.post {
                endPoint("submitAssignment")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.SuccessWithData(true)
            } else {
                Result.Error("Error submitting assignment: ${response.status}")
            }
        } catch (e: Exception) {
            Result.Error("Error submitting assignment: ${e.message}")
        }
    }

    override fun getAssignments(userId: String): Flow<Result<List<Assignment>>> = flow {
        try{
            val response = httpClient.post {
                endPoint("getAssignments")
                setBody(userId)
            }
            if(response.status == HttpStatusCode.OK){
                val assignments = response.body<List<Assignment>>()
                emit(Result.SuccessWithData(assignments))
            } else {
                emit(Result.Error("Error getting assignments: ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Error getting assignments: ${e.message}"))
        }
    }

    override suspend fun getAssignmentById(request: AssignmentRequest.GetAssignmentById): Result<Assignment> {
        return try{
            val response = httpClient.post {
                endPoint("getAssignmentById")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                val assignment = response.body<Assignment>()
                Result.SuccessWithData(assignment)
            } else {
                Result.Error("Error getting assignment: ${response.status}")
            }
        } catch (e: Exception) {
            Result.Error("Error getting assignment: ${e.message}")
        }
    }

    override fun getSubmissions(request: AssignmentRequest.GetAssignmentSubmissions): Flow<Result<List<UserAssignmentSubmission>>> = flow {
        emit (Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("getSubmissions")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                val submissions = response.body<List<UserAssignmentSubmission>>()
                emit(Result.SuccessWithData(submissions))
            } else {
                emit(Result.Error("Error getting submissions: ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Error getting submissions: ${e.message}"))

        }
    }

    override suspend fun submitAssignmentSubmissionReview(request: AssignmentRequest.SubmitReview): Result<Boolean> {
        return try{
            val response = httpClient.post {
                endPoint("submitReview")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.SuccessWithData(true)
            } else {
                Result.Error("Error submitting review: ${response.status}")
            }
        } catch (e: Exception) {
            Result.Error("Error submitting review: ${e.message}")
        }
    }
}