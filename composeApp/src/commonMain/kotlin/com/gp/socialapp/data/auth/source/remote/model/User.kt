package com.gp.socialapp.data.auth.source.remote.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String="",
    val lastName: String="",
    val password: String="",
    val profilePictureURL: String="",
    val email: String="",
    val phoneNumber: String="",
    val birthdate: LocalDateTime,
    val bio: String="",
    val createdAt: LocalDateTime,
    val isAdmin: Boolean=false,
)
