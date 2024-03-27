package com.gp.socialapp.data.post.source.remote.model

data class NestedReply(
    var reply: Reply = Reply(),
    var replies: List<NestedReply> = emptyList()
)
