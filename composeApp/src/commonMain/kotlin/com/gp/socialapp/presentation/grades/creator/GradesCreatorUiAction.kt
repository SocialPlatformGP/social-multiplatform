package com.gp.socialapp.presentation.grades.creator

sealed interface GradesCreatorUiAction {
    data class OnUploadGradesFile(val name: String, val type: String, val content: ByteArray, val subject: String):
        GradesCreatorUiAction
}