package com.gp.socialapp.data.auth.source.remote.model.requests

@kotlinx.serialization.Serializable
data class SignInRequest(
    val email: String,
    val password: String
)
