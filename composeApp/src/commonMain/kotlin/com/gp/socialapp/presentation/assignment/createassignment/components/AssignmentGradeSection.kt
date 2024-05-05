package com.gp.socialapp.presentation.assignment.createassignment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AssignmentGradeSection(
    modifier: Modifier = Modifier,
    grade: Int,
    maxGrade: Int,
    onGradeChanged: (String) -> Unit
) {
    var grade by remember{ mutableStateOf(grade.toString()) }
    val inputFilter: (String)-> String = { points ->
        points.filter { ch -> ch.isDigit() }
    }
    Column (
        modifier = Modifier.then(modifier)
    ){
        Text(text = "Grade", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = grade,
            onValueChange = {
                val temp = inputFilter(it).toIntOrNull() ?: -1
                grade = if (temp > maxGrade) maxGrade.toString() else if (temp < 0) "" else temp.toString()
                onGradeChanged(grade)
            },
            singleLine = true,
            placeholder = { Text("Grade") },
            suffix = {
                Text(text = "/$maxGrade")
            },
            modifier = Modifier.padding(vertical = 8.dp).width(TextFieldDefaults.MinWidth)
        )

    }
}