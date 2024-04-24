package com.gp.socialapp.presentation.community.createcommunity

typealias EmailDomain = String
data class CreateCommunityUiState (
    val groupName: String = "",
    val groupDescription: String = "",
    val isAdminApprovalRequired: Boolean = false,
    val allowAnyEmailDomain: Boolean = false,
    val allowedEmailDomains: List<EmailDomain> = emptyList(),
)