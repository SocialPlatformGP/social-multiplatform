package com.gp.socialapp.presentation.assignment.submitassignment

import com.gp.socialapp.data.assignment.model.AssignmentAttachment

sealed interface SubmitAssignmentUiAction {
    data object OnNavigateBack : SubmitAssignmentUiAction
    data class OnTurnInAssignment(val userAssignmentId: String, val assignmentId: String) : SubmitAssignmentUiAction
    data class OnUploadAttachment(val attach: AssignmentAttachment) : SubmitAssignmentUiAction
    data class OnUnSubmitAssignment(val userAssignmentId: String,val assignmentId: String) : SubmitAssignmentUiAction


}