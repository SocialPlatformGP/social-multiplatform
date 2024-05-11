package com.gp.socialapp.data.grades.model

@kotlinx.serialization.Serializable
data class Grades(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val communityId: String = "",
    val course: String = "",
    val grade: List<Grade> = emptyList(),
    val totalGrade: Int = grade.sumOf { it.grade }
)

@kotlinx.serialization.Serializable
data class Grade(
    val topic: String = "",
    val grade: Int = 0,
)