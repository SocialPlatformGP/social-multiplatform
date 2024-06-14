package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.MaterialMoreMenu(
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    isExpanded: Boolean,
    sheetState: SheetState,
    options: List<MaterialOptionItem>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onDismiss: () -> Unit,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    if (isExpanded && windowWidthSizeClass != WindowWidthSizeClass.Compact) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismiss,
            modifier = modifier.align(Alignment.End),
        ) {
            options.forEach {
                if(it.isAdminOption && isAdmin){
                    DropdownMenuItem(
                        text = { Text(it.label) },
                        onClick = {
                            it.onClick()
                            onDismiss()
                        }
                    )
                } else if(!it.isAdminOption){
                    DropdownMenuItem(
                        text = { Text(it.label) },
                        onClick = {
                            it.onClick()
                            onDismiss()
                        }
                    )
                }
            }
        }
    } else if(isExpanded) {
        val onDismissSheet: () -> Unit = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                onDismiss()
            }
        }
        ModalBottomSheet(
            onDismissRequest = onDismissSheet,
            sheetState = sheetState,
            modifier = modifier.align(Alignment.End)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                options.forEach {
                    Row (
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp).fillMaxWidth().clickable {
                            it.onClick()
                            onDismissSheet()
                        }
                    ){
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
data class MaterialOptionItem (
    val isAdminOption: Boolean,
    val label: String,
    val onClick: () -> Unit
)