package com.gp.socialapp.presentation.assignment.submitassignment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getExtensionFromMimeType
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class SubmitAssignmentScreen(val assignment: Assignment) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<SubmitAssignmentScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init(assignment) },
            onDisposed = { screenModel.onDispose() }
        )
        val state = screenModel.uiState.collectAsState()
        SubmitAssignmentContent(
            state = state.value,
            action = { action ->
                when (action) {
                    SubmitAssignmentUiAction.OnNavigateBack -> navigator.pop()
                    else -> screenModel.handleAction(action)
                }
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitAssignmentContent(
    state: SubmitAssignmentUiState,
    action: (SubmitAssignmentUiAction) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Submit Assignment") },
                navigationIcon = {
                    IconButton(onClick = { action(SubmitAssignmentUiAction.OnNavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(false) }
        val context = LocalPlatformContext.current
        val filePicker = rememberFilePickerLauncher(
            selectionMode = FilePickerSelectionMode.Single,
            type = FilePickerFileType.All
        ) { files ->
            uploadAssignment(scope, files, context, action)
        }
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            val assignment = state.assignment
            TopSection(
                title = assignment.title,
                dueDate = assignment.dueDate,
                creator = assignment.creatorId,
                points = assignment.maxPoints
            )
            HorizontalDivider()
            DescriptionSection(
                description = assignment.description
            )
            Spacer(modifier = Modifier.weight(1f))
            UploadSection(
                onViewUploadedAttachment = { showBottomSheet = true },
                onUploadAttachment = { filePicker.launch() },
                uploadedFileSize = assignment.attachments.size
            )

        }
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }

    }

}

@Composable
fun UploadSection(
    onUploadAttachment: () -> Unit,
    onViewUploadedAttachment: () -> Unit,
    uploadedFileSize: Int = 0
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        )
    ) {

        Text(
            text = "Upload your assignment here",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Row {
            Button(
                shape = RoundedCornerShape(4.dp),
                onClick = onUploadAttachment,
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                Text("Upload Assignment")
            }
            if (uploadedFileSize > 0) {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    onClick = onViewUploadedAttachment,
                    modifier = Modifier.weight(1f).padding(8.dp)
                ) {
                    Text("View Uploaded Assignment")
                }
            }
        }

    }

}

@Composable
fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = description,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


@Composable
fun TopSection(title: String, dueDate: Long, creator: String, points: Int) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "by $creator",
            color = MaterialTheme.colorScheme.secondary,
        )
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "$points points",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = dueDate.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }


    }
}

fun uploadAssignment(
    scope: CoroutineScope,
    files: List<KmpFile>,
    context: PlatformContext,
    action: (SubmitAssignmentUiAction) -> Unit
) {
    scope.launch {
        files.firstOrNull()?.let { file ->
            val byteFile = file.readByteArray(context)
            val mimeType = MimeType.getMimeTypeFromFileName(file.getName(context) ?: "")
            val extension = getExtensionFromMimeType(mimeType)
            action(
                SubmitAssignmentUiAction.OnUploadAttachment(
                    AssignmentAttachment(
                        name = file.getName(context) ?: "",
                        type = extension,
                        byteArray = byteFile,
                        size = byteFile.size.toLong()
                    )
                )
            )
        }
    }
}