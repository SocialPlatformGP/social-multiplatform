package com.gp.socialapp.presentation.auth.userinfo

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

data class UserInformationScreen(
    val email: String = "",
    val password: String = "",
): Screen {
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }

}
