package com.gp.socialapp.presentation.community.createcommunity

sealed class CreateCommunityAction {
    data class OnUpdateCommunityName(val name: String) : CreateCommunityAction()
    data class OnUpdateCommunityDescription(val description: String) : CreateCommunityAction()
    data class OnRequireAdminApprovalChanged(val value: Boolean) : CreateCommunityAction()
    data class OnAllowAnyEmailDomainChanged(val value: Boolean) : CreateCommunityAction()
    data class OnAddAllowedEmailDomain(val domain: String) : CreateCommunityAction()
    data class OnRemoveAllowedEmailDomain(val domain: String) : CreateCommunityAction()

    data object OnBackClicked : CreateCommunityAction()
    object OnCreateCommunityClicked : CreateCommunityAction()
}