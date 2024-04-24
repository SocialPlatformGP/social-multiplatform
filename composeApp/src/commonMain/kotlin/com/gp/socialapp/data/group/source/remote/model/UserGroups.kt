package com.gp.socialapp.data.group.source.remote.model

typealias groupId = String

@kotlinx.serialization.Serializable
data class UserGroups(
    val id: String = "",
    val groups: List<groupId> = emptyList()
)