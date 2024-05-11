package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Grading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeScreen
import com.gp.socialapp.presentation.grades.creator.GradesCreatorScreen
import com.gp.socialapp.presentation.grades.home.GradesMainScreen

data class CreatorGradesTab(val communityId:String) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Assignments"
            val icon = rememberVectorPainter(Icons.AutoMirrored.Filled.Grading)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = GradesCreatorScreen(communityId)
//            { onBottomBarVisibilityChanged(it) }
        ) { navigator ->
//            LaunchedEffect(navigator.lastItem) {
//                onBottomBarVisibilityChanged(navigator.lastItem is HomeScreen)
//            }
            SlideTransition(navigator = navigator)
        }
    }
}