package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.userprofile.UserProfileScreen
import com.gp.socialapp.theme.AppTheme
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
                UserProfileScreen("45ac4a14-8d2a-4f59-8079-e2c0f9bc5b2b")
            )
        }
    }



