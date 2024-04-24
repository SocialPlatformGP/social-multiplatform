package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DeleteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.FetchRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : PostRemoteDataSource {

    override suspend fun createPost(request: PostRequest.CreateRequest): Flow<Results<Unit, DataError.Network>> {
        return flow {
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("createPost")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    emit(Results.Success(Unit))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }
            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }
    }


    override suspend fun insertTag(tag: Tag) {
        try {
            val response = httpClient.post {
                endPoint("insertTag")
                setBody(
                    tag
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                Napier.d("insertTag: $message")
            } else {
                Napier.e("insertTag: $message")
            }
        } catch (e: Exception) {
            Napier.e("insertTag: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun getAllTags(): Flow<List<Tag>> {
        return flow {
            try {
                val response = httpClient.get {
                    endPoint("getAllTags")
                }
                if (response.status == HttpStatusCode.OK) {
                    val tags = response.body<List<Tag>>()
                    println("tags: $tags")
                    emit(tags)
                } else {
                    emit(emptyList())
                }
            } catch (e: Exception) {
                emit(emptyList())
                e.printStackTrace()
            }
        }
    }


    override fun fetchPosts(request: FetchRequest): Flow<Results<List<Post>, DataError.Network>> =
        flow {
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getNewPosts")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val posts = response.body<List<Post>>()
                    emit(Results.Success(posts))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }
            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
                e.printStackTrace()
            }
        }

    override fun fetchAllPosts(): Flow<Results<List<Post>, DataError.Network>> = flow {
        emit(Results.Loading)
        try {
            val response = httpClient.get {
                endPoint("getAllPosts")
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Results.Success(posts))
            } else {
                val error = response.body<DataError.Network>()
                emit(Results.Failure(error))
            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            e.printStackTrace()
        }
    }

    override fun searchByTitle(title: String): Flow<Results<List<Post>, DataError.Network>> = flow {
        emit(Results.Loading)
        try {
            val response = httpClient.get {
                endPoint("searchByTitle")
                setBody(title)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Results.Success(posts))
            } else {
                val error = response.body<DataError.Network>()
                emit(Results.Failure(error))
            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            e.printStackTrace()
        }
    }

    override fun searchByTag(tag: String): Flow<Results<List<Post>, DataError.Network>> = flow {
        emit(Results.Loading)
        try {
            val response = httpClient.get {
                endPoint("searchByTag")
                setBody(tag)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Results.Success(posts))
            } else {
                val error = response.body<DataError.Network>()
                emit(Results.Failure(error))
            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            e.printStackTrace()
        }
    }

    override suspend fun updatePost(request: PostRequest.UpdateRequest): Results<Unit, DataError.Network> =
        try {
            val response = httpClient.post {
                endPoint("updatePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun deletePost(request: DeleteRequest): Results<Unit, DataError.Network> =
        try {
            val response = httpClient.post {
                endPoint("deletePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun upvotePost(request: UpvoteRequest): Results<Unit, DataError.Network> =
        try {
            val response = httpClient.post {
                endPoint("upvotePost")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun downvotePost(request: DownvoteRequest): Results<Unit, DataError.Network> =
        try {
            val response = httpClient.post {
                endPoint("downvotePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun reportPost(request: PostRequest.ReportRequest): Results<Unit, DataError.Network> =
        try {
            val response = httpClient.post {
                endPoint("reportPost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }

}

