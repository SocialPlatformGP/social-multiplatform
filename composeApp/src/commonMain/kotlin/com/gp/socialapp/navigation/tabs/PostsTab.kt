package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.post.feed.FeedScreen
import kotlin.jvm.Transient

data class PostsTab(
  val communityId: String,
    @Transient
    private val onNavigation: (Boolean) -> Unit
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Posts"
            val icon = rememberVectorPainter(Icons.Rounded.Public)
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
        Navigator(screen = FeedScreen(communityId)){ navigator ->
            LaunchedEffect(navigator.lastItem) {
                onNavigation(navigator.lastItem is FeedScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }
}