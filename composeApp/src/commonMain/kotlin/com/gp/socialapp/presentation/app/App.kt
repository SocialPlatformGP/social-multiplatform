package com.gp.socialapp.presentation.app

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.gp.socialapp.di.appModules
import com.gp.socialapp.presentation.main.MainContainer
import com.gp.socialapp.presentation.post.create.CreatePostScreen
import com.gp.socialapp.presentation.post.feed.FeedScreen
import com.gp.socialapp.theme.AppTheme
import org.koin.compose.KoinApplication

@Composable
internal fun App() {
    KoinApplication(application = ({
        modules(appModules)
    })) {
        AppTheme {
            Navigator(
                MainContainer
            )
        }
    }
}

