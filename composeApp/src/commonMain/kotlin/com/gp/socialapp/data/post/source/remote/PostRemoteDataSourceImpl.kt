package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DeleteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.FetchRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
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

    override suspend fun createPost(request: PostRequest.CreateRequest): Flow<Result<Unit,PostError.CreatePost>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("createPost")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.Success(Unit))
                } else {
                    val error = response.body<PostError.CreatePost>()
                    emit(Result.Error(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(PostError.CreatePost.SERVER_ERROR))
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

    override fun getAllTags(communityId: String): Flow<List<Tag>> {
        return flow {
            try {
                val response = httpClient.post {
                    endPoint("getAllTags")
                    setBody(communityId)
                }
                if (response.status == HttpStatusCode.OK) {
                    val tags = response.body<List<Tag>>()
                    println("tags: $tags")
                    emit(tags)
                } else {
                    emit(emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(emptyList())
            }
        }
    }

    override suspend fun getUserPosts(userId: String): Result<List<Post>,PostError.GetUserPosts> {
        return try {
            val response = httpClient.post {
                endPoint("getUserPosts")
                setBody(userId)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                success(posts)
            } else {
                val serverError = response.body<PostError.GetUserPosts>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(PostError.GetUserPosts.SERVER_ERROR)
        }
    }


    override fun fetchPosts(request: FetchRequest): Flow<Result<List<Post>, PostError.GetPosts>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getNewPosts")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val posts = response.body<List<Post>>()
                    emit(Result.Success(posts))
                } else {
                    val error = response.body<PostError.GetPosts>()
                    emit(Result.Error(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(PostError.GetPosts.SERVER_ERROR))

            }
        }

    override fun fetchAllPosts(): Flow<Result<List<Post>, PostError.GetPosts>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("getAllPosts")
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Result.Success(posts))
            } else {
                val error = response.body<PostError.GetPosts>()
                emit(Result.Error(error))
            }
        } catch (e: Exception) {
            emit(Result.Error(PostError.GetPosts.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError.SearchByTitle>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("searchByTitle")
                setBody(title)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Result.Success(posts))
            } else {
                val error = response.body<PostError.SearchByTitle>()
                emit(Result.Error(error))
            }
        } catch (e: Exception) {
            emit(Result.Error(PostError.SearchByTitle.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override fun searchByTag(tag: String): Flow<Result<List<Post>, PostError.SearchByTag>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("searchByTag")
                setBody(tag)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts = response.body<List<Post>>()
                emit(Result.Success(posts))
            } else {
                val error = response.body<PostError.SearchByTag>()
                emit(Result.Error(error))
            }
        } catch (e: Exception) {
            emit(Result.Error(PostError.SearchByTag.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override suspend fun updatePost(request: PostRequest.UpdateRequest): Result<Unit, PostError.UpdatePost> =
        try {
            val response = httpClient.post {
                endPoint("updatePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                val error = response.body<PostError.UpdatePost>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.UpdatePost.SERVER_ERROR)
        }


    override suspend fun deletePost(request: DeleteRequest): Result<Unit, PostError.DeletePost> =
        try {
            val response = httpClient.post {
                endPoint("deletePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                val error = response.body<PostError.DeletePost>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.DeletePost.SERVER_ERROR)
        }


    override suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostError.UpvotePost> =
        try {
            val response = httpClient.post {
                endPoint("upvotePost")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                val error = response.body<PostError.UpvotePost>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.UpvotePost.SERVER_ERROR)
        }


    override suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostError.DownvotePost> =
        try {
            val response = httpClient.post {
                endPoint("downvotePost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                val error = response.body<PostError.DownvotePost>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.DownvotePost.SERVER_ERROR)
        }


    override suspend fun reportPost(request: PostRequest.ReportRequest): Result<Unit, PostError.ReportPost> =
        try {
            val response = httpClient.post {
                endPoint("reportPost")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                val error = response.body<PostError.ReportPost>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.ReportPost.SERVER_ERROR)
        }

}

