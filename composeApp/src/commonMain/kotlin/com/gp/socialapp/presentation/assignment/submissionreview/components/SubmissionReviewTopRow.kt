package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.auth.source.remote.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionReviewTopRow(
    modifier: Modifier = Modifier,
    currentSubmission: UserAssignmentSubmission,
    submissions: List<UserAssignmentSubmission>,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onSubmissionSelected: (String) -> Unit,
    onSubmitReviewClicked: () -> Unit
) {
    var isExpanded by remember{ mutableStateOf(false) }

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(modifier)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ){
            IconButton(
                onClick = onPreviousClicked
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Previous"
                )
            }

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {isExpanded = it}
            ) {
                TextField(
                    value = currentSubmission.userName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false}
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(submissions.size) { index ->
                            DropdownMenuItem(
                                text = {
                                   Text(text = submissions[index].userName)
                                },
                                onClick = {
                                    onSubmissionSelected(submissions[index].id)
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            IconButton(
                onClick = onNextClicked,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Next"
                )
            }
        }
        Button(
            onClick = onSubmitReviewClicked,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                text = "Submit Review"
            )
        }
    }
}