package com.gp.socialapp.data.post.source.remote.model

data class NestedReply(
    var reply: Reply?,
    var replies: List<NestedReply>
)
