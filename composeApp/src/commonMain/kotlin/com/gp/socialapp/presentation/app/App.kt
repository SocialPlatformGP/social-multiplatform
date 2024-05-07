package com.gp.socialapp.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.di.appModuleK
import com.gp.socialapp.presentation.assignment.createassignment.CreateAssignmentScreen
import com.gp.socialapp.presentation.assignment.submissionreview.SubmissionReviewScreen
import com.gp.socialapp.presentation.assignment.submissionreview.components.SubmissionAttachmentPreview
import com.gp.socialapp.presentation.assignment.submitassignment.SubmitAssignmentScreen
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.theme.AppTheme
import io.ktor.http.ContentDisposition.Companion.Attachment
import org.kodein.di.compose.withDI

@Composable
internal fun App() =
    withDI(appModuleK) {
        AppTheme {
            Navigator(
                LoginScreen
            )
        }
    }



