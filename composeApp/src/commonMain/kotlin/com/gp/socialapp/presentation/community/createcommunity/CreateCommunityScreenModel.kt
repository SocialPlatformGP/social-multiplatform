package com.gp.socialapp.presentation.community.createcommunity

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateCommunityScreenModel(

): ScreenModel {
    private val _uiState = MutableStateFlow(CreateCommunityUiState())
    val uiState = _uiState.asStateFlow()
}