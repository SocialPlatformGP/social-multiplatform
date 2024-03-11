package com.gp.socialapp.data.auth.source.remote.model.requests
@kotlinx.serialization.Serializable
data class IsEmailAvailableRequest(
    val email: String="" ,
)
