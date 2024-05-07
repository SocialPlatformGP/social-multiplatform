package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeScreen

data class CreatorAssignmentTab(val communityId:String) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Assignments"
            val icon = rememberVectorPainter(Icons.AutoMirrored.Filled.Assignment)

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