package com.gp.socialapp.presentation.grades.GradeDetails

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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.grades.model.Grades

data class GradeDetailsScreen(val grades: List<Grades>) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        GradeDetailsScreenContent(grades = grades) {
            navigator.pop()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeDetailsScreenContent(
    grades: List<Grades>,
    navigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                title = {
                    Text(grades.first().course)
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            LazyColumn(
                Modifier.fillMaxWidth().padding(8.dp)
            ) {
                items(grades) { grades ->
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Row(
                            Modifier.fillMaxWidth().padding(4.dp)
                        ) {
                            Text(
                                text = grades.userName,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Row(
                            Modifier.fillMaxWidth().padding(4.dp)
                        ) {
                            Text(
                                text = "Topic",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                            Text(
                                text = "MaxPoints",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                            Text(
                                text = "Grade",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                        }
                        HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        grades.grade.forEach { grade ->
                            Row(
                                Modifier.fillMaxWidth().padding(4.dp)
                            ) {
                                Text(
                                    text = grade.topic.split("[")[0],
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).fillMaxWidth()
                                )
                                Text(
                                    text = grade.maxPoints.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).fillMaxWidth()
                                )
                                Text(
                                    text = grade.grade.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f).fillMaxWidth()
                                )
                            }
                            HorizontalDivider(thickness = 1.dp)
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            Text(
                                text = "Total",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                                )
                            Text(
                                text = grades.grade.sumOf { it.maxPoints }.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                            Text(
                                text = grades.grade.sumOf { it.grade }.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                        }
                    }
                }

            }
        }

    }
}


