package com.gp.socialapp.presentation.assignment.homeassignment

import com.gp.socialapp.data.assignment.model.Assignment

data class AssignmentHomeUiState(
    val isLoading: Boolean = false,
    val assignments: List<Assignment> = emptyList(),
    val error: String = ""
)