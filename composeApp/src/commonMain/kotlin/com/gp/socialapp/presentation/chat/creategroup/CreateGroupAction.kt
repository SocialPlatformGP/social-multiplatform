package com.gp.socialapp.presentation.chat.creategroup

import com.gp.socialapp.data.auth.source.remote.model.User

sealed class CreateGroupAction {
    data class OnUpdateName(val name: String) : CreateGroupAction()
    data class OnSetError(val value: Boolean) : CreateGroupAction()
    data class OnUnselectUser(val userId: String) : CreateGroupAction()
    data class OnSelectUser(val userId: String) : CreateGroupAction()
    data class OnImagePicked(val array: ByteArray) : CreateGroupAction()
    object OnCreateGroup : CreateGroupAction()
}