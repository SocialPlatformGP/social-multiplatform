package com.gp.socialapp.presentation.grades.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                item {
                    Row (
                        Modifier.background(color = MaterialTheme.colorScheme.primaryContainer).padding(4.dp)
                    ){
                        Text(text = "Name",Modifier.weight(3f), textAlign = TextAlign.Center)
                        Text(text = "Course",Modifier.weight(1f), textAlign = TextAlign.Center)
                        state.grades.firstOrNull()?.let {
                            it.grade.forEach {
                                Text(text = it.topic,Modifier.weight(1f), textAlign = TextAlign.Center)
                            }
                        }
                        Text(text = "Total",Modifier.weight(1f), textAlign = TextAlign.Center)

                    }
                }
                items(state.grades) {grades->

                    Column {
                        Row {
                            Text(text = grades.userName,Modifier.weight(3f), textAlign = TextAlign.Center)
                            Text(text = grades.course,Modifier.weight(1f), textAlign = TextAlign.Center)
                            grades.grade.forEach {grade->
                                Text(text = grade.grade.toString(),Modifier.weight(1f), textAlign = TextAlign.Center)
                            }
                            Text(text = grades.grade.sumOf { it.grade }.toString(),Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}