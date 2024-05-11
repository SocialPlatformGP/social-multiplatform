package com.gp.socialapp.presentation.grades

sealed interface GradesMainUiAction {
    data class OnUploadGradesFile(val name: String, val type: String, val content: ByteArray, val subject: String, val communityId: String): GradesMainUiAction
}