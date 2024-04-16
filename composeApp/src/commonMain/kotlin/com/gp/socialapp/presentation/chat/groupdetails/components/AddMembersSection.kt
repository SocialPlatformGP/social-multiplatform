package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors.MyIconPack
import com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors.myiconpack.AddPeopleCircle

@Composable
fun AddMembersSection(
    onAddMembersClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .fillMaxWidth()
            .clickable {
                onAddMembersClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = MyIconPack.AddPeopleCircle,
            contentDescription = null,
            modifier = Modifier
                .size(55.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = modifier.width(12.dp))
        Text(
            text = "Add Members",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
        )
    }
}