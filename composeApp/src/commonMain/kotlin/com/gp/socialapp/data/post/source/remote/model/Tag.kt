package com.gp.socialapp.data.post.source.remote.model

@kotlinx.serialization.Serializable
data class Tag(
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
)
