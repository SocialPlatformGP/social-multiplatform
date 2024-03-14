package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyOutlinedTextButton(
    onClick: () -> Unit,
    label: String,
) {
    OutlinedButton(
        onClick = onClick, shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(
                    top = 3.dp, bottom = 3.dp, start = 4.dp, end = 4.dp
                )
        )
    }
}