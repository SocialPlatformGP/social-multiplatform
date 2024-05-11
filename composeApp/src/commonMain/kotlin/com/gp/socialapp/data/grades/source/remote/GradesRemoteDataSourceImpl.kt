package com.gp.socialapp.data.grades.source.remote

import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.data.grades.source.remote.model.GradesRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GradesRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : GradesRemoteDataSource {
    override fun getGrades(userName: String): Flow<Result<List<Grades>>> = flow{
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("getUserGrades")
                setBody(GradesRequest.GetGrades(userName))
            }
            if(response.status == HttpStatusCode.OK) {
                val data = response.body<List<Grades>>()
                emit(Result.SuccessWithData(data))
            } else {
                emit(Result.Error("Server error: ${response.status.value} ${response.status.description}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message?: "An error occurred"))
        }
    }

    override suspend fun uploadGradesFile(
        name: String,
        type: String,
        content: ByteArray,
        subject: String,
        communityId: String
    ) {
        val request = GradesRequest.UploadGradesFile(
            name = name,
            type = type,
            content = content,
            subject = subject,
            communityId = communityId)
        try {
            val response = httpClient.post{
                endPoint("uploadGradesFile")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("Server error: ${response.status.value} ${response.status.description}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message?: "An error occurred")
        }
    }
}