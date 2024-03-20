package com.gp.socialapp.data.post.source.remote.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FetchPostsRequest(
    val lastUpdated: Long
)
