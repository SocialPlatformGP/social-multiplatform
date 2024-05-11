package com.gp.socialapp.presentation.grades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

object GradesMainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<GradesHomeScreenModel>()
        val state by screenModel.state.collectAsState()

        GradeMainScreenContent(
            state = state,
            actions = {}
        )


    }

}

@Composable
fun GradeMainScreenContent(
    state: GradesHomeState,
    actions: (GradesMainUiAction) -> Unit
) {
    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    val filePicker = rememberFilePickerLauncher {
        scope.launch {
            val file = it.first()
            val name = file.getName(context)?.toString() ?: "Unknown"
            val type = MimeType.getMimeTypeFromFileName(name)
            val content = file.readByteArray(context)
            val extension = MimeType.getExtensionFromMimeType(type)
            actions(
                GradesMainUiAction.OnUploadGradesFile(
                    name,
                    extension,
                    content,
                    "Math",
                    "1"
                )
            )
        }

    }
    Scaffold(
        floatingActionButton = {
            if (state.user.isAdmin)
                FloatingActionButton(
                    onClick = {
                        filePicker.launch()
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
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(state.grades) {
                    Column {
                        Text(text = it.userName)
                        Row (
                            Modifier.background(color = androidx.compose.ui.graphics.Color.Gray)
                        ){
                            Text(text = "Course")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = it.course)
                        }
                        it.grade.forEach { grade ->
                            Row {
                                Text(text = grade.topic)
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = grade.grade.toString())
                            }
                        }
                        Row {
                            Text(text = "Total Grade")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = it.grade.sumOf { it.grade }.toString())
                        }
                    }
                }
            }
        }
    }
}