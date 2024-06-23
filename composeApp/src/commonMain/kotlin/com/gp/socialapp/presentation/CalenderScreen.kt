//package com.gp.socialapp.presentation
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import cafe.adriel.voyager.core.screen.Screen
//import io.wojciechosak.calendar.animation.CalendarAnimator
//import io.wojciechosak.calendar.config.SelectionMode
//import io.wojciechosak.calendar.config.rememberCalendarState
//import io.wojciechosak.calendar.range.RangeConfig
//import io.wojciechosak.calendar.range.RoundedRangeIllustrator
//import io.wojciechosak.calendar.utils.Pallete
//import io.wojciechosak.calendar.utils.today
//import io.wojciechosak.calendar.view.CalendarView
//import kotlinx.datetime.LocalDate
//import kotlinx.datetime.Month
//
//object CalenderScreen:Screen {
//    @Composable
//    override fun Content() {
//        val startDate by remember { mutableStateOf(LocalDate.today()) }
//
//        val calendarAnimator by remember { mutableStateOf(CalendarAnimator(startDate)) }
//        val coroutineScope = rememberCoroutineScope()
//        CalendarView(
//            config = rememberCalendarState(
//                startDate = startDate,
//                monthOffset = 12,
//            ), onDateSelected = { day->
//
//            },
//            selectionMode = SelectionMode.Single,
//            rangeConfig = RangeConfig(rangeIllustrator = RoundedRangeIllustrator(Pallete.LightGreen)),
//        )  }
//
//}
