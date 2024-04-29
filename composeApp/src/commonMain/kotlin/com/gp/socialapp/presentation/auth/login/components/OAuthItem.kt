package com.gp.socialapp.presentation.auth.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.gotrue.providers.OAuthProvider

@Composable
fun OAuthProviderItem(
    modifier: Modifier = Modifier,
    provider: MyOAuthProvider,
    onClick: (OAuthProvider) -> Unit,
    isEnabled: Boolean,
) {
    IconButton(
        modifier = modifier,
        enabled = isEnabled,
        onClick = {
        onClick(provider.provider)
    }) {
        Icon(
            tint = Color.Unspecified,
            imageVector = provider.icon,
            contentDescription = provider.name,
            modifier = Modifier.size(40.dp)
        )
    }
}

data class MyOAuthProvider(
    val name: String, val icon: ImageVector, val provider: OAuthProvider
)