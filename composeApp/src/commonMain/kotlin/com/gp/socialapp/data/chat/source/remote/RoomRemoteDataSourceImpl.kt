package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.reponse.RoomResponse
import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomRemoteDataSourceImpl(
    private val httpClient: HttpClient
): RoomRemoteDataSource {
    override suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post{
                endPoint("createGroupRoom")
                setBody(
                    request
                )
            }
            if(response.status == HttpStatusCode.OK){
                val roomId = response.body<RoomResponse.CreateGroupRoom>().roomId
                emit(Result.SuccessWithData(roomId))
            } else {
                emit(Result.Error("An error occurred: ${response.status.description}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("An error occurred: ${e.message}"))
        }
    }
}