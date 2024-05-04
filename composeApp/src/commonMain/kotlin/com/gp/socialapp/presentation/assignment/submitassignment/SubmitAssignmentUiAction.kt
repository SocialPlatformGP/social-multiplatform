package com.gp.socialapp.presentation.assignment.submitassignment

import com.gp.socialapp.data.assignment.model.AssignmentAttachment

sealed interface SubmitAssignmentUiAction {
    data object OnNavigateBack : SubmitAssignmentUiAction
    data class OnUploadAttachment(val attach: AssignmentAttachment) : SubmitAssignmentUiAction


}