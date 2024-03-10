package com.gp.socialapp.data.auth.source.remote.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: LocalDateTime = LocalDateTime.now(),
    val bio: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isAdmin: Boolean = false,
)
