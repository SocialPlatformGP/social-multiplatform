package com.gp.socialapp.presentation.auth.userinfo

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.datetime.LocalDateTime

data class UserInformationUiState(
    val signedInUser: User? = null,
    var name: String = "",
    var phoneNumber: String = "",
    var birthDate: LocalDateTime = LocalDateTime.now(),
    var bio: String = "",
    var pfpImageByteArray: ByteArray = byteArrayOf(),
    var error: AuthError = AuthError.NoError,
    val createdState: Result<Unit,UserError> = Result.idle()
)