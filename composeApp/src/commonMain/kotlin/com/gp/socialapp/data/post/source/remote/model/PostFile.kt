package com.gp.socialapp.data.post.source.remote.model

@kotlinx.serialization.Serializable
data class PostFile(
    val url: String = "",
    val name: String = "",
    val type: MimeType,
    val size: Long = 0
)

