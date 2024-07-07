package com.gp.socialapp.presentation.calendar.createevent


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.repository.CalendarRepository
import com.gp.socialapp.presentation.calendar.home.EventType
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateEventScreenModel(
    private val authRepo: AuthenticationRepository,
    private val calendarRepo: CalendarRepository
) : ScreenModel{
    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState = _uiState.asStateFlow()
    fun init(commId : String){
        getSignedInUser()
        _uiState.update {
            it.copy(communityId = commId)
        }
    }

    private fun getSignedInUser() {
        screenModelScope.launch (DispatcherIO){
            authRepo.getSignedInUser().let { result ->
                when(result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                    }

                    is Result.Error -> {}
                    Result.Loading -> {}
                }

            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = CreateEventUiState()
    }

    fun handleUiAction(action: CreateCalendarUiAction) {
        return when(action){
            is CreateCalendarUiAction.CreateEvent -> {
                createEvent()
            }
            is CreateCalendarUiAction.EventTitleChanged -> {
                updateTitle(action.title)
            }
            is CreateCalendarUiAction.EventDescriptionChanged -> {
                updateDescription(action.description)
            }
            is CreateCalendarUiAction.EventDateChanged -> {
                updateDate(action.date)
            }
            is CreateCalendarUiAction.EventTimeChanged -> {
                updateTime(action.time)
            }
            is CreateCalendarUiAction.EventLocationChanged -> {
                updateLocation(action.location)
            }
            is CreateCalendarUiAction.EventTypeChanged -> {
                updateEventType(action.type)
            }
            is CreateCalendarUiAction.EventAssignToChanged -> {
                updateAssignTo(action.isPrivate)
            }
            else -> Unit
        }
    }

    private fun updateAssignTo(assignTo: Boolean) {
        _uiState.update {
            it.copy(isPrivate = assignTo)
        }
    }

    private fun updateEventType(type: String) {
        println("Type: $type")
        _uiState.update {
            it.copy(type = type)
        }
    }

    private fun updateLocation(location: String) {
        _uiState.update {
            it.copy(location = location)
        }
    }

    private fun updateTime(time: Long) {
        _uiState.update {
            it.copy(time = time)
        }
    }

    private fun updateDate(date: Long) {
        _uiState.update {
            it.copy(date = date)
        }
    }

    private fun updateDescription(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    private fun createEvent() {
        screenModelScope.launch (DispatcherIO){
            calendarRepo.createUserEvent(
                userId = _uiState.value.currentUser.id,
                event = CalendarEvent(
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    type = _uiState.value.type,
                    date = _uiState.value.date,
                    time = _uiState.value.time,
                    location = _uiState.value.location,
                    user = _uiState.value.currentUser.name,
                    isPrivate = _uiState.value.isPrivate,
                    communityId = _uiState.value.communityId
                )
            ).let {
                when(it){
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(isCreated = true)
                        }
                    }
                    is Result.Error -> {}
                    Result.Loading -> {}
                }
            }
        }
    }
}