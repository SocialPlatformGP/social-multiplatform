package com.gp.socialapp.presentation.auth.userinfo

import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import kotlinx.datetime.LocalDateTime

data class UserInformationUiState(
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var birthDate: LocalDateTime = LocalDateTime.now(),
    var bio: String = "",
    var pfpImageByteArray: ByteArray = byteArrayOf(),
    var error: AuthError = AuthError.NoError,
    val createdState: Result<AuthResponse> = Result.Idle
)