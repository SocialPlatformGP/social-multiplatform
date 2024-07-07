package com.gp.socialapp.presentation.calendar.createevent

interface CreateCalendarUiAction {
    data object BackPressed: CreateCalendarUiAction
    data object CreateEvent: CreateCalendarUiAction
    data class EventTitleChanged(val title: String): CreateCalendarUiAction
    data class EventDescriptionChanged(val description: String): CreateCalendarUiAction
    data class EventDateChanged(val date: Long): CreateCalendarUiAction
    data class EventTimeChanged(val time: Long): CreateCalendarUiAction
    data class EventLocationChanged(val location: String): CreateCalendarUiAction
    data class EventTypeChanged(val type: String): CreateCalendarUiAction
    data class  EventAssignToChanged(val isPrivate: Boolean): CreateCalendarUiAction
}