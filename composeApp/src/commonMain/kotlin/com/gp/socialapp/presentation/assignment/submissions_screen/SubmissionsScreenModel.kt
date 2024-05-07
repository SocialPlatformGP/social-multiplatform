package com.gp.socialapp.presentation.assignment.submissions_screen

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubmissionsScreenModel(

):ScreenModel {
    private val _uiState = MutableStateFlow(SubmissionsScreenUiState())
    val uiState = _uiState.asStateFlow()
}