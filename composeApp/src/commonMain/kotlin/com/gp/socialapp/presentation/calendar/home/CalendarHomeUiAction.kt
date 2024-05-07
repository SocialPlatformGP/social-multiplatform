package com.gp.socialapp.presentation.calendar.home


sealed interface CalendarHomeUiAction {
    data object CreateEvent : CalendarHomeUiAction
}