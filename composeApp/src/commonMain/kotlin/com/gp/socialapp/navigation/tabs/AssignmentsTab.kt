package com.gp.socialapp.navigation.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeScreen
import com.gp.socialapp.presentation.home.screen.HomeScreen

data class AssignmentsTab(val communityId:String = "") : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Assignments"
            val icon = rememberVectorPainter(Icons.AutoMirrored.Filled.Assignment)

            return remember {
                TabOptions(
                    index = if(communityId.isEmpty()) 1u else 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = AssignmentHomeScreen(communityId)
//            { onBottomBarVisibilityChanged(it) }
        ) { navigator ->
//            LaunchedEffect(navigator.lastItem) {
//                onBottomBarVisibilityChanged(navigator.lastItem is HomeScreen)
//            }
            SlideTransition(navigator = navigator)
        }
    }
}