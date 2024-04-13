package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageFileAttachment(
    fileType: String,
    fileName: String,
    onFileClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        color = Color(0f, 0f, 0f, 0.15f),
        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onFileClicked() }.padding(8.dp)) {
            Icon(
                painter = getFilePainterResource(fileType),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = fileName,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun getFilePainterResource(fileType: String): Painter {
    TODO("Not yet implemented")
//    painterResource(
//        id = when {
//            fileType.contains("application/pdf") -> R.drawable.ic_pdf
//            fileType.contains("audio") -> R.drawable.ic_audio
//            fileType.contains("video") -> R.drawable.ic_video
//            fileType.contains("text") -> R.drawable.ic_text
//            fileType.contains("wordprocessingml") || fileType.contains("msword") -> R.drawable.ic_word
//            fileType.contains("powerpoint") || fileType.contains("presentation") -> R.drawable.ic_ppt
//            fileType.contains("excel") || fileType.contains("spreadsheet") -> R.drawable.ic_excel
//            else -> R.drawable.ic_file
//        }
//    )
}