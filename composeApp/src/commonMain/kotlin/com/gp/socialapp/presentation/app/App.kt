package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.post.create.CreatePostScreen
import com.gp.socialapp.presentation.post.feed.FeedTab
import com.gp.socialapp.theme.AppTheme
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
            LoginScreen
            )
//            Navigator(
//                ChatRoomScreen(
//                    roomId = 20,
//                    roomTitle = "Room 1",
//                    roomAvatarUrl = "",
//                    isPrivate = false
//                )
//            )
        }
    }