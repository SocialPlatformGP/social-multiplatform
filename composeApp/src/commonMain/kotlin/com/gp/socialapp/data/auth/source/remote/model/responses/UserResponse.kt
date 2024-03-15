package com.gp.socialapp.data.auth.source.remote.model.responses

import com.gp.socialapp.data.auth.source.remote.model.User

@kotlinx.serialization.Serializable
data class UserResponse(
    val id: String = "",
    val isAdmin: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = 0L,
    val bio: String = "",
    val createdAt: String = "",
) {
    fun toUser() = User(
        id = id,
        isAdmin = isAdmin,
        firstName = firstName,
        lastName = lastName,
        profilePictureURL = profilePictureURL,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        bio = bio,
    )
}