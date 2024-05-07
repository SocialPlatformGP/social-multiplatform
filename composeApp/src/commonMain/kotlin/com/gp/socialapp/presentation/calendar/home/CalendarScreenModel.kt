package com.gp.socialapp.presentation.calendar.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalendarHomeScreenModel (
    private val authRepo: AuthenticationRepository,
): ScreenModel {
    private val _uiState = MutableStateFlow(CalendarHomeUiState())
    val uiState = _uiState.asStateFlow()
    fun init() {
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        /*TODO: Handle Error*/
                    }
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                        getUserEvents()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun getUserEvents() {
        TODO("Not yet implemented")
    }
}