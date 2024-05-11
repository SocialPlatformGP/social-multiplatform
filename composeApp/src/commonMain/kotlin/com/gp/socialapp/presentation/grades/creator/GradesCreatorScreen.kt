package com.gp.socialapp.presentation.grades.creator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

data class GradesCreatorScreen(val communityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<GradesCreatorScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init(communityId) }
        )
        val state = screenModel.state.collectAsState()
        GradesCreatorContent(
            state = state.value,
            actions = { action ->
                when (action) {
                    is GradesCreatorUiAction.OnUploadGradesFile -> {
                        screenModel.uploadGradesFile(
                            action.name,
                            action.type,
                            action.content,
                            action.subject,
                        )
                    }
                }

            }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesCreatorContent(
    state: GradesCreatorUiState,
    actions: (GradesCreatorUiAction) -> Unit
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    var dialogState by remember { mutableStateOf(false) }
    var courseName by remember { mutableStateOf("") }
    val filePicker = rememberFilePickerLauncher {
        if (it.isEmpty()) return@rememberFilePickerLauncher
        scope.launch {
            val file = it.first()
            val name = file.getName(context) ?: "Unknown"
            val type = MimeType.getMimeTypeFromFileName(name)
            val content = file.readByteArray(context)
            val extension = MimeType.getExtensionFromMimeType(type)
            actions(
                GradesCreatorUiAction.OnUploadGradesFile(
                    name,
                    extension,
                    content,
                    courseName,
                )
            )

        }

    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogState = true
                }
            ) {
                Icon(
                    Icons.Default.UploadFile,
                    contentDescription = "Upload Grades"
                )
            }
        }

    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            var dropdownMenuState by remember { mutableStateOf(false) }
            var subjects = state.grades.map { it.course }.distinct()
            var selectedSubject by remember { mutableStateOf("Select Course") }
            var data: List<Grades> by remember { mutableStateOf(emptyList()) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                LazyRow(
                    Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    items(subjects) { subject ->
                        FilterChip(
                            selected = selectedSubject == subject,
                            label = { Text(text = subject) },
                            onClick = {
                                selectedSubject = subject
                                dropdownMenuState = false
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            LazyColumn(
                Modifier.fillMaxWidth().padding(8.dp)
            ) {

                data = state.grades.filter { it.course == selectedSubject }
                item {
                    Row(
                        Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
                            .padding(4.dp)
                    ) {
                        Text(text = "Name", Modifier.weight(3f), textAlign = TextAlign.Center)
                        Text(text = "Course", Modifier.weight(1f), textAlign = TextAlign.Center)
                        data.firstOrNull()?.let {
                            it.grade.forEach {
                                Text(
                                    text = it.topic,
                                    Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Text(text = "Total", Modifier.weight(1f), textAlign = TextAlign.Center)

                    }
                }
                items(data) { grades ->

                    Column {
                        Row {
                            Text(
                                text = grades.userName,
                                Modifier.weight(3f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = grades.course,
                                Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            grades.grade.forEach { grade ->
                                Text(
                                    text = grade.grade.toString(),
                                    Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                text = grades.grade.sumOf { it.grade }.toString(),
                                Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
        if (dialogState) {
            var errorMessage by remember { mutableStateOf("") }
            courseName = ""
            AlertDialog(
                onDismissRequest = { dialogState = false },
                title = { Text("Course Name") },
                text = {
                    Column {
                        Text("Enter the Course Name ")
                        TextField(
                            value = courseName,
                            onValueChange = {
                                courseName = it
                                errorMessage = ""
                            },
                            label = { Text("Course Name") },
                            isError = errorMessage.isNotEmpty(),
                            supportingText = { Text(errorMessage) }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (courseName.isEmpty()){
                                errorMessage = "Course Name is required"
                                return@TextButton
                            }
                            dialogState = false
                            filePicker.launch()
                        }
                    ) {
                        Text("Next")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { dialogState = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}