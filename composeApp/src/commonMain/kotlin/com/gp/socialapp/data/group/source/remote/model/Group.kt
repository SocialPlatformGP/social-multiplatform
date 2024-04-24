package com.gp.socialapp.data.group.source.remote.model

typealias UserId = String
typealias domain = String
typealias isAdmin = Boolean

@kotlinx.serialization.Serializable
data class Group(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val members: Map<UserId, isAdmin> = emptyMap(),
    val isAdminApprovalRequired: Boolean = false,
    val allowedEmailDomains: List<domain> = emptyList(),
)

