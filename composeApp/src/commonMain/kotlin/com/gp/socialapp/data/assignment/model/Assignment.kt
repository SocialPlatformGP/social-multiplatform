package com.gp.socialapp.data.assignment.model

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val title: String = "",
    val description: String = "",
    val id: String = "",
    val attachments: List<AssignmentAttachment> = emptyList(),
    val maxPoints: Int = 10,
    val dueDate: Long = 0,
    val acceptLateSubmissions: Boolean = false,
    val createdAt: Long = 0,
    val creatorId: String = "",
    val creatorName:String = "",
    val communityId: String = "",
)
