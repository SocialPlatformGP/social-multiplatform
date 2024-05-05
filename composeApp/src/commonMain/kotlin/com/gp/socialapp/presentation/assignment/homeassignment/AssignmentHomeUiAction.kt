package com.gp.socialapp.presentation.assignment.homeassignment

import com.gp.socialapp.data.assignment.model.Assignment

sealed interface AssignmentHomeUiAction {
    data object OnBackClicked : AssignmentHomeUiAction
    data object OnFabClicked : AssignmentHomeUiAction
    data class OnAssignmentClicked(val assignment: Assignment) : AssignmentHomeUiAction
}