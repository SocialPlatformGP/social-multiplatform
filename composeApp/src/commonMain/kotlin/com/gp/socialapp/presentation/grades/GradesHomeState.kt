package com.gp.socialapp.presentation.grades

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.grades.model.Grades

data class GradesHomeState (
    val isLoading: Boolean = false,
    val grades: List<Grades> = emptyList(),
    val error: String = "",
    val user :User= User()
)

