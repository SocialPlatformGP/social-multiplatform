package com.gp.socialapp.presentation.community.createcommunity.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.gp.socialapp.presentation.community.createcommunity.EmailDomain

@Composable
fun AllowedEmailDomainItem(
    modifier: Modifier = Modifier,
    emailDomain: EmailDomain,
    onRemoveAllowedEmailDomain: (EmailDomain) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "*@$emailDomain",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = { onRemoveAllowedEmailDomain(emailDomain) }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove allowed email domain",
            )
        }
    }
}