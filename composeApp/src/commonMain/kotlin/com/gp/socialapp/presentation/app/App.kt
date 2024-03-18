package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.post.feed.FeedScreen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.gp.material.presentation.MaterialScreen
import com.gp.socialapp.di.appModules
import com.gp.socialapp.presentation.auth.signup.SignUpScreen

import com.gp.socialapp.theme.AppTheme
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
//                UserInformationScreen("", "")
//                EditPostScreen(
//                    Post(
//                        title = "title",
//                        body = "body",
//                    )
//                )


                MaterialScreen
            )
        }
    }



