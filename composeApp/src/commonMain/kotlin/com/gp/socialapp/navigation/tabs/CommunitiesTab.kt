package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.home.screen.HomeScreen
import com.gp.socialapp.presentation.home.screen.HomeUiAction
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Users
import compose.icons.tablericons.Home
import compose.icons.tablericons.User
import compose.icons.tablericons.Users
import kotlin.jvm.Transient

data class CommunitiesTab(
    @Transient
    val onBottomBarVisibilityChanged: (Boolean) -> Unit,
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Communities"
            val icon = rememberVectorPainter(TablerIcons.Users)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = HomeScreen { onBottomBarVisibilityChanged(it) }
        ) { navigator ->
            LaunchedEffect(navigator.lastItem) {
                onBottomBarVisibilityChanged(navigator.lastItem is HomeScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }
}