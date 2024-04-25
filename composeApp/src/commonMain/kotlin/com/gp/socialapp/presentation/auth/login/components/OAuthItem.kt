package com.gp.socialapp.presentation.auth.login.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.jan.supabase.gotrue.providers.OAuthProvider

@Composable
fun OAuthProviderItem(
    modifier: Modifier = Modifier, provider: MyOAuthProvider, onClick: (OAuthProvider) -> Unit
) {
    IconButton(modifier = modifier, onClick = {
        onClick(provider.provider)
    }) {
        Icon(
            imageVector = provider.icon, contentDescription = provider.name
        )
    }
}

data class MyOAuthProvider(
    val name: String, val icon: ImageVector, val provider: OAuthProvider
)