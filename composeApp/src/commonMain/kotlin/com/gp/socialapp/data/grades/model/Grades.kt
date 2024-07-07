package com.gp.socialapp.data.grades.model

import korlibs.math.roundDecimalPlaces

@kotlinx.serialization.Serializable
data class Grades(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val communityId: String = "",
    val course: String = "",
    val grade: List<Grade> = emptyList(),
    val totalGrade: Int = grade.sumOf { it.grade },
    val creatorId: String = "",
){
    fun getTotalGrades() = grade.sumOf { it.grade }
    fun getTotalMaxPoints() = grade.sumOf { it.maxPoints }

}
fun List<Grades>.getGradesByCourse(course: String) = filter { it.course == course }
fun List<Grades>.getTotalGrades(course: String) = getGradesByCourse(course).sumOf { it.getTotalGrades() }
fun List<Grades>.getTotalMaxPoints(course: String) = getGradesByCourse(course).sumOf { it.getTotalMaxPoints() }
fun List<Grades>.getPercentage(course: String) = (getTotalGrades(course) / getTotalMaxPoints(course).toFloat() * 100).roundDecimalPlaces(2).toString() + " %"
fun List<Grades>.getRank(course: String) = getGradesByCourse(course).sortedBy { it.getTotalGrades() }.indexOfFirst { it.getTotalGrades() == getTotalGrades(course) } + 1

@kotlinx.serialization.Serializable
data class Grade(
    val topic: String = "",
    val grade: Int = 0,
    val maxPoints: Int = 0,
)