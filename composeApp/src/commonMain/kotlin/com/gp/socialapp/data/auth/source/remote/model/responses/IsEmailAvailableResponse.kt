package com.gp.socialapp.data.auth.source.remote.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class IsEmailAvailableResponse(
    val isAvailable: Boolean,
    val message: String,
)
