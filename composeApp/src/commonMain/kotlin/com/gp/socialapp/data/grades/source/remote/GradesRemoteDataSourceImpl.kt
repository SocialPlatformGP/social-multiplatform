package com.gp.socialapp.data.grades.source.remote

import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.data.grades.source.remote.model.GradesRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.GradesError
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
    override fun getGrades(userName: String): Flow<Result<List<Grades>,GradesError>> = flow{
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("getUserGrades")
                setBody(GradesRequest.GetGrades(userName))
            }
            if(response.status == HttpStatusCode.OK) {
                val data = response.body<List<Grades>>()
                emit(Result.Success(data))
            } else {
                emit(Result.Error(GradesError.SERVER_ERROR))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(GradesError.SERVER_ERROR))
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
                println("Grades uploaded successfully")
            } else {
                println("Error uploading grades")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}