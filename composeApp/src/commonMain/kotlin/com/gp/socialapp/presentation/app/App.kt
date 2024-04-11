package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.post.feed.FeedScreen
import com.gp.socialapp.presentation.post.searchResult.SearchResultScreen
import com.gp.socialapp.theme.AppTheme
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
                FeedScreen
            )
        }
    }



