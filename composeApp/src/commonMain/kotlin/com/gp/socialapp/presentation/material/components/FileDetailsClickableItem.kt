package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gp.socialapp.util.AppConstants.BASE_URL

@Composable
fun FileDetailsClickableItem(
    title: String,
    url: String,
    icon: ImageVector,
    onOpenLinkClicked: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        val link = BASE_URL + url
        append(link)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline
            ), start = 0, end = link.length
        )
        addStringAnnotation(
            tag = "URL", annotation = link, start = 0, end = link.length
        )

    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(4.dp).size(40.dp)
                .background(MaterialTheme.colorScheme.onSecondary, MaterialTheme.shapes.medium)
        )
        Column {
            Text(
                text = title,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            ClickableText(text = annotatedString,
                modifier = Modifier.padding(start = 8.dp),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "URL", start = offset, end = offset
                    ).firstOrNull()?.let {
                        onOpenLinkClicked(url)
                    }
                }

            )
        }
    }
}