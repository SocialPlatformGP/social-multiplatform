package com.gp.socialapp.presentation.grades.creator

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.grades.model.Grade
import com.gp.socialapp.data.grades.model.Grades

data class GradesCreatorUiState(
    val grades : List<Grades> = emptyList(),
    val communityId: String = "",
    val user :User = User(),
)