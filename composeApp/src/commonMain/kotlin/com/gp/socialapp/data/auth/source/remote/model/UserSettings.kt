package com.gp.socialapp.data.auth.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userId: String = ""
)
