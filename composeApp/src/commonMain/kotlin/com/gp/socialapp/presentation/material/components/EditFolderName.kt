package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.material.model.MaterialFolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFolderName(
    onDismissRequest: () -> Unit = {},
    folder: MaterialFolder,
    onRenameFolderClicked: (String) -> Unit
) {
    var text by remember { mutableStateOf(folder.name) }
    BasicAlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        modifier = Modifier.wrapContentSize(),
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "New folder name", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.align(
                    Alignment.Start))
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text("Folder Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            onRenameFolderClicked(text)
                            onDismissRequest()
                        },
                        enabled = text.isNotBlank(),
                        modifier = Modifier.padding(8.dp).padding(end = 8.dp)
                    ) {
                        Text("Rename")
                    }
                }

            }
        }
    }
}