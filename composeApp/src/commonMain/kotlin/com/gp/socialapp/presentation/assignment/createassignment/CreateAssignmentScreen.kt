package com.gp.socialapp.presentation.assignment.createassignment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.presentation.assignment.createassignment.components.AssignmentAttachmentsSection
import com.gp.socialapp.presentation.assignment.createassignment.components.CreateAssignmentDescription
import com.gp.socialapp.presentation.assignment.createassignment.components.CreateAssignmentDueDate
import com.gp.socialapp.presentation.assignment.createassignment.components.CreateAssignmentTitle
import com.gp.socialapp.presentation.assignment.createassignment.components.CreateAssignmentTopBar
import com.gp.socialapp.presentation.settings.components.NumberSettingItem
import com.gp.socialapp.presentation.settings.components.SwitchSettingItem

data class CreateAssignmentScreen(val communityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CreateAssignmentScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.init(communityId)
            },
            onDisposed = {
                screenModel.onDispose()
            }
        )
        if(state.isCreated){
            //TODO: Navigate to the created assignment
        }
        CreateAssignmentContent(
            acceptLateSubmissions = state.acceptLateSubmissions,
            maxPoints = state.maxPoints,
            attachments = state.attachments,
            onAction = { action ->
                when (action) {
                    is CreateAssignmentUiAction.BackPressed -> {
                        navigator.pop()
                    }

                    else -> screenModel.handleUiAction(action)
                }
            }
        )
    }

    @Composable
    fun CreateAssignmentContent(
        modifier: Modifier = Modifier,
        acceptLateSubmissions: Boolean,
        maxPoints: Int,
        attachments: List<AssignmentAttachment>,
        onAction: (CreateAssignmentUiAction) -> Unit,
    ) {
        Scaffold(modifier = modifier, topBar = {
            CreateAssignmentTopBar(onBack = { onAction(CreateAssignmentUiAction.BackPressed) },
                onCreate = { onAction(CreateAssignmentUiAction.CreateAssignment) })
        }) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(it).padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CreateAssignmentTitle { title ->
                    onAction(CreateAssignmentUiAction.TitleChanged(title))
                }
                CreateAssignmentDescription { description ->
                    onAction(CreateAssignmentUiAction.DescriptionChanged(description))
                }
                CreateAssignmentDueDate {
                    onAction(CreateAssignmentUiAction.DueDateChanged(it))
                }
                SwitchSettingItem(
                    name = "Accept late submissions",
                    isChecked = acceptLateSubmissions,
                    icon = Icons.Default.MoreTime,
                ) {
                    onAction(CreateAssignmentUiAction.AcceptLateSubmissionsChanged(it))
                }
                NumberSettingItem(
                    title = "Max points",
                    icon = Icons.Default.Tag,
                    state = maxPoints.toString(),
                    inputFilter = { points ->
                        points.filter { ch -> ch.isDigit() }
                    },
                    onCheck = { points ->
                        points.toIntOrNull() != null
                    },
                    onSave = { points ->
                        onAction(CreateAssignmentUiAction.MaxPointsChanged(points.toInt()))
                    },
                )
                AssignmentAttachmentsSection(
                    attachments = attachments,
                    onAddAttachment = { name, type, size, byteArray ->
                        onAction(
                            CreateAssignmentUiAction.AttachmentAdded(
                                name,
                                type,
                                size,
                                byteArray
                            )
                        )
                    },
                    onRemoveAttachment = { attachment ->
                        onAction(CreateAssignmentUiAction.AttachmentRemoved(attachment))
                    }
                )
            }

        }
    }
}