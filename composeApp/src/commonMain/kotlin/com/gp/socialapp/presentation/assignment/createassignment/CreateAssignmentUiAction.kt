package com.gp.socialapp.presentation.assignment.createassignment

import com.gp.socialapp.data.assignment.model.AssignmentAttachment

sealed interface CreateAssignmentUiAction {
    data class TitleChanged(val title: String) : CreateAssignmentUiAction
    data class DescriptionChanged(val description: String) : CreateAssignmentUiAction
    data class DueDateChanged(val dueDate: Long) : CreateAssignmentUiAction
    data class MaxPointsChanged(val maxPoints: Int) : CreateAssignmentUiAction
    data class AcceptLateSubmissionsChanged(val acceptLateSubmissions: Boolean) :
        CreateAssignmentUiAction

    data class AttachmentAdded(
        val name: String,
        val type: String,
        val size: Long,
        val byteArray: ByteArray
    ) : CreateAssignmentUiAction

    data class AttachmentRemoved(val attachment: AssignmentAttachment) : CreateAssignmentUiAction

    object CreateAssignment : CreateAssignmentUiAction
    object BackPressed : CreateAssignmentUiAction
}