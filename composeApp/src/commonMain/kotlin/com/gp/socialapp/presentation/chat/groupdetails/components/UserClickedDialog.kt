package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.navigation.tabs.GradesTab.options
import com.gp.socialapp.presentation.home.components.OptionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserClickedDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    options: List<OptionItem>,
    sheetState: SheetState,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    when(windowWidthSizeClass){
        WindowWidthSizeClass.Compact -> {
            CompactUserClickedDialog(
                modifier = modifier,
                sheetState = sheetState,
                options = options,
                onDismiss = onDismiss,
            )
        }
        else -> {
            ExpandedUserClickedDialog(
                modifier = modifier,
                options = options,
                onDismiss = onDismiss,
            )
        }
    }
}
@Composable
private fun ExpandedUserClickedDialog(
    modifier: Modifier = Modifier,
    options: List<OptionItem>,
    onDismiss: () -> Unit,
){
    Dialog(
        onDismissRequest = onDismiss,
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
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
                            onDismiss()
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactUserClickedDialog(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    options: List<OptionItem>,
    onDismiss: () -> Unit,
){
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        modifier = modifier
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
                        onDismiss()
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