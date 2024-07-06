package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.util.LocalDateTimeUtil.getDateHeader
import korlibs.time.DateTimeTz

@Composable
fun DateHeader(
    modifier: Modifier = Modifier,
    createdAt: DateTimeTz,
) {
    val header = createdAt.getDateHeader()
    Row(modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        HorizontalDivider(
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Column(
            modifier = Modifier.padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(4.dp)
                )) {
            Text(
                text = header,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        HorizontalDivider(
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }
}