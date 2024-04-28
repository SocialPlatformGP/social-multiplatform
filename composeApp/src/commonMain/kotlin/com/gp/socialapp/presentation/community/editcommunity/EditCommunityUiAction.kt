package com.gp.socialapp.presentation.community.editcommunity

import com.gp.socialapp.presentation.community.createcommunity.EmailDomain

sealed interface EditCommunityUiAction {
    data class OnUpdateCommunityName(val name: String) : EditCommunityUiAction
    data class OnUpdateCommunityDescription(val description: String) : EditCommunityUiAction
    data class OnRequireAdminApprovalChanged(val value: Boolean) : EditCommunityUiAction
    data class OnAllowAnyEmailDomainChanged(val value: Boolean) : EditCommunityUiAction
    data class OnAddAllowedEmailDomain(val domain: EmailDomain) : EditCommunityUiAction
    data class OnRemoveAllowedEmailDomain(val domain: EmailDomain) : EditCommunityUiAction

    data object OnBackClicked : EditCommunityUiAction
    data object OnSaveClicked : EditCommunityUiAction
}
