package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModules
import com.gp.socialapp.presentation.post.create.CreatePostScreen
import com.gp.socialapp.theme.AppTheme
import org.koin.compose.KoinApplication

@Composable
internal fun App() {
    KoinApplication(application = ({
        modules(appModules)
    })) {
        AppTheme {
            Navigator(
                CreatePostScreen
            )
        }
    }
}

