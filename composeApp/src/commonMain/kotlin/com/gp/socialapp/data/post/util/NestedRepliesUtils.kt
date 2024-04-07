package com.gp.socialapp.data.post.util

import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.data.post.source.remote.model.Reply

object ToNestedReplies {
    fun List<Reply>.toNestedReplies(): NestedReply {
        val nestedRepliesList = buildNestedReplies(this, null)
        return NestedReply(null, replies = nestedRepliesList)


    }

    private fun buildNestedReplies(
        replies: List<Reply>,
        parentReplyId: String?
    ): List<NestedReply> {
        return replies
            .filter { it.parentReplyId == parentReplyId }
            .map { reply ->
                val nestedReplies = buildNestedReplies(replies, reply.id)
                NestedReply(
                    reply = reply,
                    replies = nestedReplies
                )
            }
    }



}