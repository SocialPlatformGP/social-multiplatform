package com.gp.socialapp.data.auth.source.remote.model.requests

@kotlinx.serialization.Serializable
data class GetUserRequest(
    val id: String = "",
)