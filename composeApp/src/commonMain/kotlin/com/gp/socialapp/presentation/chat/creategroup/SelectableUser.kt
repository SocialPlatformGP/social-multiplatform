package com.gp.socialapp.presentation.chat.creategroup

import com.gp.socialapp.data.auth.source.remote.model.User

data class SelectableUser(
    val user: User, var isSelected: Boolean = false
) {
    companion object {
        fun User.toSelectableUser() = SelectableUser(this)
    }
}
