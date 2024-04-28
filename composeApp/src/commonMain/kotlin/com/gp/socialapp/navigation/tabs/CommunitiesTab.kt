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
import com.gp.socialapp.presentation.home.HomeScreen
import com.gp.socialapp.presentation.home.HomeUiAction
import kotlin.jvm.Transient

data class CommunitiesTab(
    @Transient
    val onBottomBarVisibilityChanged: (Boolean) -> Unit,
    @Transient
    val action: (HomeUiAction) -> Unit
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Communities"
            val icon = rememberVectorPainter(Icons.Default.Diversity3)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = HomeScreen(
                onBottomBarVisibilityChanged,
                action = action
            )
        ) { navigator ->
            LaunchedEffect(navigator.lastItem) {
                onBottomBarVisibilityChanged(navigator.lastItem is HomeScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }
}