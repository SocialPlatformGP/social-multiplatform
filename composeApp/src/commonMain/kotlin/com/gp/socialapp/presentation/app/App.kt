package com.gp.socialapp.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.material.MaterialScreen
import com.gp.socialapp.theme.AppTheme
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
                LoginScreen
            )
        }
    }