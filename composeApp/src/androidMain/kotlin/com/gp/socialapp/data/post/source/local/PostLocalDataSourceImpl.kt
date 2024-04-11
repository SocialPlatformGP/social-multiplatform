package com.gp.socialapp.data.post.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.gp.socialapp.data.post.source.local.model.PostEntity
import com.gp.socialapp.data.post.source.local.model.PostEntity.Companion.toPost
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Post.Companion.toEntity
import com.gp.socialapp.db.AppDatabase
import com.gp.socialapp.db.PostQueries
import com.gp.socialapp.db.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostLocalDataSourceImpl(
    private val db: AppDatabase
) : PostLocalDataSource {
    val postQueries: PostQueries = db.postQueries
    override suspend fun insertPost(post: Post) {
        val entity = post.toEntity()
        with(entity) {
            postQueries.insert(
                id = id,
                title = title,
                body = body,
                reply_count = replyCount.toLong(),
                author_name = authorName,
                author_pfp = authorPfp,
                author_id = authorID,
                created_at = createdAt.toLong(),
                votes = votes.toLong(),
                downvotes = downvoted,
                upvotes = upvoted,
                moderation_status = moderationStatus,
                edited_status = editedStatus.toLong(),
                tags = tags,
                type = type,
                attachments = attachments,
                last_modified = lastModified.toLong()
            )
        }
    }

    override fun getAllPosts(): Flow<List<Post>> {
        return postQueries.getAll().asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map {
                PostEntity(
                    replyCount = it.reply_count.toInt(),
                    authorName = it.author_name,
                    authorPfp = it.author_pfp,
                    id = it.id,
                    authorID = it.author_id,
                    createdAt = it.created_at,
                    title = it.title,
                    body = it.body,
                    votes = it.votes.toInt(),
                    downvoted = it.downvotes,
                    upvoted = it.upvotes,
                    moderationStatus = it.moderation_status,
                    editedStatus = it.edited_status.toInt(),
                    tags = it.tags,
                    type = it.type,
                    attachments = it.attachments,
                    lastModified = it.last_modified
                ).toPost()
            }
        }
    }

    override fun getPostById(id: String): Flow<Post> {
        return postQueries.getById(id).asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { posts ->
                PostEntity(
                    replyCount = posts.reply_count.toInt(),
                    authorName = posts.author_name,
                    authorPfp = posts.author_pfp,
                    id = posts.id,
                    authorID = posts.author_id,
                    createdAt = posts.created_at,
                    title = posts.title,
                    body = posts.body,
                    votes = posts.votes.toInt(),
                    downvoted = posts.downvotes,
                    upvoted = posts.upvotes,
                    moderationStatus = posts.moderation_status,
                    editedStatus = posts.edited_status.toInt(),
                    tags = posts.tags,
                    type = posts.type,
                    attachments = posts.attachments,
                    lastModified = posts.last_modified
                ).toPost()
            }.first()
        }
    }

    override suspend fun deletePostById(id: String) {
        postQueries.deleteWithId(id)
    }

    override suspend fun deleteAllPosts() {
        postQueries.deleteAll()
    }

    override fun searchByTitle(title: String): Flow<List<Post>> {
        return postQueries.searchByTitle(title).asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map {
                PostEntity(
                    replyCount = it.reply_count.toInt(),
                    authorName = it.author_name,
                    authorPfp = it.author_pfp,
                    id = it.id,
                    authorID = it.author_id,
                    createdAt = it.created_at,
                    title = it.title,
                    body = it.body,
                    votes = it.votes.toInt(),
                    downvoted = it.downvotes,
                    upvoted = it.upvotes,
                    moderationStatus = it.moderation_status,
                    editedStatus = it.edited_status.toInt(),
                    tags = it.tags,
                    type = it.type,
                    attachments = it.attachments,
                    lastModified = it.last_modified
                ).toPost()
            }
        }
    }

    override fun searchByTag(tag: String): Flow<List<Post>> {
        return postQueries.searchByTag(tag).asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map {
                PostEntity(
                    replyCount = it.reply_count.toInt(),
                    authorName = it.author_name,
                    authorPfp = it.author_pfp,
                    id = it.id,
                    authorID = it.author_id,
                    createdAt = it.created_at,
                    title = it.title,
                    body = it.body,
                    votes = it.votes.toInt(),
                    downvoted = it.downvotes,
                    upvoted = it.upvotes,
                    moderationStatus = it.moderation_status,
                    editedStatus = it.edited_status.toInt(),
                    tags = it.tags,
                    type = it.type,
                    attachments = it.attachments,
                    lastModified = it.last_modified
                ).toPost()
            }
        }
    }
}