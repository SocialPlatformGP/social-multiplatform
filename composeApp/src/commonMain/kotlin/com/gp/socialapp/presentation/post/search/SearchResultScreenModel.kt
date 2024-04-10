package com.gp.socialapp.presentation.post.search

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchResultScreenModel: ScreenModel {
    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()
}