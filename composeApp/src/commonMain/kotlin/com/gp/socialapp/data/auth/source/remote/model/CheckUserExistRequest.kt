package com.gp.socialapp.data.auth.source.remote.model
@kotlinx.serialization.Serializable
data class CheckUserExistRequest(
    val email: String="" ,
    val password: String=""
)
