package com.gp.socialapp.presentation.assignment.submissionreview

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.data.assignment.repository.AssignmentRepository
import com.gp.socialapp.data.material.repository.MaterialRepository
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubmissionReviewScreenModel(
    private val assignmentRepo: AssignmentRepository,
    private val materialRepo: MaterialRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(SubmissionReviewUiState())

    val uiState = _uiState.asStateFlow()
    fun init(assignmentId: String, submissionId: String) {
        getCurrentAssignmentAndSubmissions(assignmentId)
        getSubmissions(assignmentId, submissionId)
    }

    private fun getCurrentAssignmentAndSubmissions(assignmentId: String) {
        screenModelScope.launch(DispatcherIO) {
            assignmentRepo.getAssignmentById(assignmentId).let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentAssignment = result.data)
                        }
                    }

                    is Result.Error -> {
                        println("Error: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getSubmissions(assignmentId: String, initialSubmissionId: String) {
        screenModelScope.launch(DispatcherIO) {
            assignmentRepo.getSubmissions(assignmentId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                submissions = result.data,
                                currentSubmissionId = result.data.firstOrNull()?.id ?: ""
                            )
                        }
                        if (initialSubmissionId.isNotEmpty()) {
                            _uiState.update {
                                it.copy(currentSubmissionId = initialSubmissionId)
                            }
                        }
                    }

                    is Result.Error -> {
                        println("Error: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    fun submitAction(action: SubmissionReviewUiAction) {
        when (action) {
            is SubmissionReviewUiAction.AttachmentClicked -> {
                _uiState.update {
                    it.copy(currentPreviewedAttachmentId = action.attachmentId)
                }
            }

            is SubmissionReviewUiAction.FeedbackChanged -> {
                updateFeedback(action.feedback)
            }

            is SubmissionReviewUiAction.GradeChanged -> {
                updateGrade(action.grade)
            }

            SubmissionReviewUiAction.SubmitReview -> {
                submitReview()
            }

            is SubmissionReviewUiAction.ViewNext -> {
                updateCurrentSubmission(action.nextId)
            }

            is SubmissionReviewUiAction.ViewPrevious -> {
                updateCurrentSubmission(action.previousId)
            }

            is SubmissionReviewUiAction.ViewSubmission -> {
                updateCurrentSubmission(action.submissionId)
            }

            is SubmissionReviewUiAction.DownloadAttachment -> {
                downloadAttachment(action.attachment)
            }

            else -> Unit
        }
    }

    private fun downloadAttachment(attachment: AssignmentAttachment) {
        val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
        val fullMime = MimeType.getFullMimeType(mimeType)
        screenModelScope.launch {
            materialRepo.openFile(attachment.id,attachment.url,fullMime)
        }
    }

    private fun updateCurrentSubmission(submissionId: String) {
        _uiState.update {
            it.copy(currentSubmissionId = submissionId)
        }
    }

    private fun submitReview() {
        screenModelScope.launch(DispatcherIO) {
            with(_uiState.value) {
                assignmentRepo.submitAssignmentSubmissionReview(
                    submissionId = currentSubmissionId,
                    grade = grade,
                    feedback = feedback,
                ).let { result ->
                    when (result) {
                        is Result.Success -> {
                            println("Review submitted successfully")
                        }

                        is Result.Error -> {
                            println("Error: ${result.message}")
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun updateGrade(grade: String) {
        grade.toIntOrNull().let { grade ->
            if (grade != null) {
                _uiState.update {
                    it.copy(grade = grade)
                }
            }
        }
    }

    private fun updateFeedback(feedback: String) {
        _uiState.update {
            it.copy(feedback = feedback)
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = SubmissionReviewUiState()
    }
}
