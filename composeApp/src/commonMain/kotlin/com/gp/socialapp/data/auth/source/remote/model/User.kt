package com.gp.socialapp.data.auth.source.remote.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = 0L,
    val bio: String = "",
    val createdAt: String = "",
    val isAdmin: Boolean = false,
) {
    fun toUserRequest() = UserRequest(
        firstName = firstName,
        lastName = lastName,
        password = password,
        profilePictureURL = profilePictureURL,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate ?: LocalDateTime.now().toMillis(),
        bio = bio,
        isAdmin = isAdmin
    )
}

@Serializable
data class UserRequest(
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = LocalDateTime.now().toMillis(),
    val bio: String = "",
    val isAdmin: Boolean = false,
)
