package com.gp.socialapp.presentation.auth.userinfo

import com.eygraber.uri.Uri
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import kotlinx.datetime.LocalDateTime
data class UserInformationUiState (
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var birthDate:  LocalDateTime = LocalDateTime.now(),
    var bio: String ="",
    var pfpLocalURI: Uri = Uri.EMPTY,
    val createdState: Result<Nothing> = Result.Idle
)