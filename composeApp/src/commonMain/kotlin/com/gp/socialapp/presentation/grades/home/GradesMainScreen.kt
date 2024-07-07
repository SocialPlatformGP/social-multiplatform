package com.gp.socialapp.presentation.grades.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.data.grades.model.getPercentage
import com.gp.socialapp.presentation.grades.GradeDetails.GradeDetailsScreen

object GradesMainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<GradesHomeScreenModel>()
        val state by screenModel.state.collectAsState()

        GradeMainScreenContent(
            state = state,
            navigateToGradeDetails = {
                navigator.push(GradeDetailsScreen(grades = it))
            }
        )
    }
}

@Composable
fun GradeMainScreenContent(
    state: GradesHomeState,
    navigateToGradeDetails: (List<Grades>) -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            var subjects = state.grades.map { it.course }.distinct()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                items(subjects) { subject ->
                    Card(
                        onClick = {
                            navigateToGradeDetails(state.grades.filter { it.course == subject })
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp).fillMaxHeight()
                            .padding(8.dp)

                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = subject,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Score : ",

                                )
                                Text(
                                    text = state.grades.getPercentage(course = subject),
                                    style = MaterialTheme.typography.headlineMedium
                                )

                            }
                        }
                    }
                }


            }
        }
    }
}