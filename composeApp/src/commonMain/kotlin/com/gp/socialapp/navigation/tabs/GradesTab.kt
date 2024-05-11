package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.gp.socialapp.presentation.grades.home.GradesMainScreen

object GradesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Grades"
            val icon = rememberVectorPainter(Icons.Rounded.Description)

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
                Navigator(screen = GradesMainScreen)

    }
}