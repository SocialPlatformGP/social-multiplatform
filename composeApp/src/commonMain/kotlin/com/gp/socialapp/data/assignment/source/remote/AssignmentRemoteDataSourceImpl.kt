package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.post.util.endPoint
import io.ktor.client.HttpClient
import com.gp.socialapp.util.Result
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class AssignmentRemoteDataSourceImpl(
    private val httpClient: HttpClient,
): AssignmentRemoteDataSource {
    override suspend fun createAssignment(assignment: Assignment): Result<String> {
        return try{
            val response = httpClient.post {
                endPoint("createAssignment")
                setBody(assignment)
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
}