package com.gp.socialapp.presentation.community.editcommunity

import com.gp.socialapp.presentation.community.createcommunity.EmailDomain

data class EditCommunityUiState(
    val communityName: String = "",
    val communityDescription: String = "",
    val requireAdminApproval: Boolean = false,
    val allowAnyEmailDomain: Boolean = true,
    val allowedEmailDomains: Set<EmailDomain> = emptySet(),
    val isFinished: Boolean = false,
)
