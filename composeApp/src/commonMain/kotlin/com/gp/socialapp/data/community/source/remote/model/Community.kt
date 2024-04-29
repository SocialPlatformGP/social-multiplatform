package com.gp.socialapp.data.community.source.remote.model

typealias UserId = String
typealias domain = String
typealias isAdmin = Boolean

@kotlinx.serialization.Serializable
data class Community(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val members: Map<UserId, isAdmin> = emptyMap(),
    val isAdminApprovalRequired: Boolean = false,
    val allowAnyEmailDomain: Boolean = true,
    val allowedEmailDomains: Set<domain> = emptySet(),
)

