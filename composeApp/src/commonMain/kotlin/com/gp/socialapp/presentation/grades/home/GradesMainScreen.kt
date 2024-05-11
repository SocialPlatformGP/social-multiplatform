package com.gp.socialapp.presentation.grades.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

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
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            LazyColumn(
                Modifier.fillMaxWidth().padding(8.dp)
            ) {
                items(state.grades) { grades ->
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Row(
                            Modifier.fillMaxWidth()
                        ) {
                            Column(
                                Modifier.weight(3f),
                            ) {
                                Text(
                                    text = "Name",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onTertiary)
                                )
                                Text(
                                    text = grades.userName,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Column(
                                Modifier.weight(1f),
                            ) {
                                Text(
                                    text = "Course",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onTertiary)
                                )
                                Text(
                                    text = grades.course,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            grades.grade.forEach { grade ->
                                Column(
                                    Modifier.weight(1f),
                                ) {
                                    Text(
                                        text = grade.topic,
                                        textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onTertiary)
                                    )
                                    Text(
                                        text = grade.grade.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                            Column(
                                Modifier.weight(1f),
                            ) {
                                Text(
                                    text = "Total",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onTertiary)
                                )
                                Text(
                                    text = grades.grade.sumOf { it.grade }.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}