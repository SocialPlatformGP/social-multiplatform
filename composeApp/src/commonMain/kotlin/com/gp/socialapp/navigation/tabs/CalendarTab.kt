package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.calendar.home.CalendarHomeScreen
import kotlin.jvm.Transient

data class CalendarTab(
    @Transient
    private val onNavigation: (Boolean) -> Unit,
    val communityId: String,
): Tab {
    override val options: TabOptions
    @Composable
    get() {
        val title = "Calendar"
        val icon = rememberVectorPainter(Icons.Rounded.CalendarMonth)

        return remember {
            TabOptions(
                index = 3u,
                title = title,
                icon = icon
            )
        }
    }

    @Composable
    override fun Content() {
        //TODO
        Navigator(screen = com.gp.socialapp.presentation.calendar.home.CalendarHomeScreen(
            communityId
        )
        ) { navigator ->
            LaunchedEffect(navigator.lastItem) {
                onNavigation(navigator.lastItem is CalendarHomeScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }
}