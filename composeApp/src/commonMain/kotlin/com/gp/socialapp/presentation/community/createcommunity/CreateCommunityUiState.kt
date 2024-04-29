package com.gp.socialapp.presentation.community.createcommunity

import com.gp.socialapp.data.community.source.remote.model.Community

typealias EmailDomain = String
data class CreateCommunityUiState (
    val createdCommunity: Community? = null,
    val communityName: String = "",
    val communityDescription: String = "",
    val requireAdminApproval: Boolean = false,
    val allowAnyEmailDomain: Boolean = true,
    val allowedEmailDomains: Set<EmailDomain> = emptySet(),
)