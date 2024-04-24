package com.gp.socialapp.presentation.community.createcommunity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.community.createcommunity.EmailDomain

@Composable
fun AllowedEmailDomainsSection(
    modifier: Modifier = Modifier,
    allowedEmailDomains: Set<EmailDomain>,
    onAddEmailDomain: (EmailDomain) -> Unit,
    onRemoveEmailDomain: (EmailDomain) -> Unit,
) {
    Column (
        modifier = modifier.fillMaxWidth(),
    ){
        AddAllowedDomainSection(
            onAddAllowedDomain = onAddEmailDomain,
        )
        LazyColumn (
            contentPadding = PaddingValues(vertical = 8.dp)
        ){
            items(allowedEmailDomains.size) { index ->
                AllowedEmailDomainItem(
                    emailDomain = allowedEmailDomains.elementAt(index),
                    onRemoveAllowedEmailDomain = onRemoveEmailDomain,
                )
            }

        }
    }
}