package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.util.LocalDateTimeUtil.convertEpochToTime

@Composable
fun RecentChatDate(date: Long) {
    Text(
        text = convertEpochToTime(date),
        modifier = Modifier.padding(end = 8.dp)
    )
}