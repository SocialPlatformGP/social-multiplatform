package com.gp.socialapp.presentation.calendar.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.repository.CalendarRepository
import com.gp.socialapp.presentation.calendar.home.components.EventType
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class CalendarHomeScreenModel (
    private val authRepo: AuthenticationRepository,
    private val calendarRepo: CalendarRepository
): ScreenModel {
    private val _uiState = MutableStateFlow(CalendarHomeUiState())
    val uiState = _uiState.asStateFlow()
    fun init() {
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch (DispatcherIO){
            authRepo.getSignedInUser().let { result ->
                result.onSuccessWithData { user ->
                    _uiState.update {
                        it.copy(currentUser = user)
                    }
                    getUserEvents()
                }.onFailure {
                    /*TODO: Handle Error*/
                }
            }
        }
    }

    private fun getUserEvents() {
        screenModelScope.launch (DispatcherIO){
            calendarRepo.getUserEvents(_uiState.value.currentUser.id).collect { result ->
                result.onSuccessWithData {  events ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            events = events
                        )
                    }
                }.onFailure {
                    /*TODO handle error*/
                }.onLoading {
                    /*TODO handle loading*/
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value= CalendarHomeUiState()
    }
}