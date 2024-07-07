package com.gp.socialapp.presentation.assignment.submitassignment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
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
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.presentation.post.create.component.AddNewFileButton
import com.gp.socialapp.util.AppConstants.BASE_URL
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import com.seiko.imageloader.rememberImagePainter
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
                },
                actions = {

                    Button(
                        enabled = state.oldSubmission.attachments.isNotEmpty(),
                        onClick = {
                            if (state.oldSubmission.isTurnedIn) {
                                action(
                                    SubmitAssignmentUiAction.OnUnSubmitAssignment(
                                        state.oldSubmission.id,
                                        state.assignment.id
                                    )
                                )
                            } else {
                                action(
                                    SubmitAssignmentUiAction.OnTurnInAssignment(
                                        state.oldSubmission.id,
                                        state.oldSubmission.assignmentId
                                    )
                                )
                            }
                        }
                    ) {
                        val text = if (state.oldSubmission.isTurnedIn) "UnSubmit" else "Submit"
                        Text(text = text)
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
                creator = assignment.creatorName,
                points = assignment.maxPoints
            )
            HorizontalDivider()
            DescriptionSection(
                description = assignment.description
            )
            Spacer(modifier = Modifier.weight(1f))
            UploadSection(
                onUploadAttachment = {
                    filePicker.launch()
                },
                attachments = state.oldSubmission.attachments,
                onFileDelete = {
                },
                onAction = { action(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
        if (showBottomSheet) {
            AssignmentBottomSheet(
                attachments = state.oldSubmission.attachments,
                onDismiss = { showBottomSheet = false },
                state = sheetState
            )
        }

    }

}

@Composable
fun UploadSection(
    onUploadAttachment: () -> Unit,
    attachments: List<AssignmentAttachment>,
    onFileDelete: (AssignmentAttachment) -> Unit,
    onAction: (SubmitAssignmentUiAction) -> Unit = { },

    ) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        item {
            AddNewFileButton(Modifier.padding(horizontal = 8.dp), onClick = onUploadAttachment)

        }
        items(attachments) { attachment ->
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Unspecified,
                                style = androidx.compose.ui.graphics.drawscope.Fill,
                                cornerRadius = CornerRadius(8.dp.toPx())
                            )
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onFileDelete(attachment) }.padding(horizontal = 2.dp)
                ) {

                    val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
                    if (attachments.isEmpty()) {
                        when (mimeType) {
                            is MimeType.Image -> Image(
                                painter = rememberImagePainter(BASE_URL + attachment.url),
                                contentDescription = null,
                                contentScale = androidx.compose.ui.layout.ContentScale.FillHeight,
                                modifier = Modifier.fillMaxSize()
                            )

                            else -> Icon(
                                tint = Color.Unspecified,
                                imageVector = getFileImageVector(mimeType),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                            )
                        }
                    } else if (attachment.byteArray.isNotEmpty() && (mimeType is MimeType.Image)) {
                        Image(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center),
                            bitmap = attachment.byteArray.toImageBitmap(),
                            contentDescription = null,
                            contentScale = androidx.compose.ui.layout.ContentScale.FillHeight

                        )
                    } else {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = getFileImageVector(mimeType),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                        )
                    }

                }
                Text(
                    text = attachment.name,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.width(70.dp)
                )
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
            style = MaterialTheme.typography.headlineMedium,
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
                text = "Possible Points: $points",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = dueDate.toLocalDateTime().dayOfMonth.toString() + " / " + dueDate.toLocalDateTime().monthNumber.toString() + " / " + dueDate.toLocalDateTime().year.toString(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentBottomSheet(
    modifier: Modifier = Modifier,
    attachments: List<AssignmentAttachment>,
    onDismiss: () -> Unit,
    state: SheetState,
    onAction: (SubmitAssignmentUiAction) -> Unit = { },
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = state
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(8.dp),
        ) {
            items(attachments) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.Start)
                        .padding(4.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))

                ) {
                    val mimeType = MimeType.getMimeTypeFromFileName(it.name)
                    Image(
                        imageVector = getFileImageVector(mimeType),
                        contentDescription = "file",
                        modifier = Modifier.size(75.dp)
                            .clickable { /*TODO*/ },
                    )

                    Spacer(modifier = Modifier.size(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        verticalArrangement = Arrangement.SpaceAround,
                    ) {
                        Text(
                            text = "Name: " + it.name,
                            maxLines = 1,
                        )
                        Text(
                            text = "Type: " + it.type,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}