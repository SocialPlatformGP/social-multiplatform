package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.FetchPostsRequest
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

private const val BASE_URL = "http://192.168.1.4:8080/"

class PostRemoteDataSourceImpl : PostRemoteDataSource {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
                encodeDefaults = true
            })
        }
    }

    private fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createPost(post: Post): Flow<Result<String>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("createPost")
                    setBody(
                        post
                    )
                }
                val message = response.bodyAsText()
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(message))
                } else {
                    emit(Result.Error(message))
                }
            } catch (e: Exception) {
                Napier.e("createPost: ${e.message}")
                emit(Result.Error(e.message ?: "An unknown error occurred"))
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
        }
    }

    override fun getAllTags(): Flow<List<Tag>> {
        return flow {
            try {
                val response = httpClient.get {
                    endPoint("getAllTags")
                }
                println("response: ${response.status}")
                if (response.status == HttpStatusCode.OK) {
                    val tags = response.body<List<Tag>>()
                    println("tags: $tags")
                    emit(tags)
                } else {
                    emit(emptyList())
                }
            } catch (e: Exception) {
                Napier.e("getAllTags: ${e.message}")
                emit(emptyList())
            }
        }
    }


    override fun fetchPosts(request: FetchPostsRequest): Flow<List<Post>> {
        println("fetchPosts: $request *********************125")
        return flow {
            try {
                val response = httpClient.post {
                    endPoint("getNewPosts")
                    setBody(
                        request
                    )
                }
                println("response: ${response.status}")
                if (response.status == HttpStatusCode.OK) {
                    val posts = response.body<List<Post>>()
                    println("posts: $posts")
                    emit(posts)
                } else {
                    emit(emptyList())
                }
            } catch (e: Exception) {
                Napier.e("getAllPosts: ${e.message}")
                println("getAllPosts: ${e.message}")
                emit(emptyList())
            }
        }
    }

    override suspend fun updatePost(post: Post): Flow<Result<String>> {
        return flow { }
    }

    override suspend fun deletePost(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun upVotePost(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun downVotePost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun fetchPostById(id: String): Flow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun incrementReplyCounter(postId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun decrementReplyCounter(postId: String) {
        TODO("Not yet implemented")
    }


}