package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.presentation.post.searchResult.components.SearchResultItem
import com.gp.socialapp.theme.AppTheme
import com.mohamedrejeb.calf.picker.FilePickerFileType
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidApp() : Application() {
//    override val di = DI.lazy {
//        appModuleK
//        bind<Context>() with singleton { applicationContext }
//    }

    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Napier.base(DebugAntilog())
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }


}
@Preview
@Composable
fun PreviewApp() {
    AppTheme {
        Column{
            SearchResultItem(
                item = post,
                onPostClicked = {},
                modifier = Modifier.padding(16.dp))
        }

    }
}
val post = Post(
    id = "sfgs",
    authorName = "John Doe",
    authorPfp = "https://example.com/john-doe.jpg",
    title = "Hello, World My Name is what my name is who my name is nigga nigga nigga slim shady!",
    body = "sbdbsjkbg,asjbgjbsajgaskjbkasbdkjjbaskdjbfkasjjbfkjbasdkfbaskjbfkjsbdkfjbskfdbaksjdbfkjabd",
    createdAt = 11633165L,
    votes = 42,
    replyCount = 3,
    attachments = listOf(
        PostAttachment(
            type = FilePickerFileType.ImageContentType,
        )
    )
)


//@Preview
//@Composable
//fun PreviewApp() {
//    App()
//}

//@Preview
//@Composable
//fun AttachmentItemPreview() {
//    val post = Post(
//        title = "Hello, My name is who? my name is what?",
//        body = "My name is nigga nigga nigga slim shady",
//        replyCount = 5,
//        userName = "Eminem",
//        publishedAt = "Tomorrow",
//        votes = 15,
//        attachments = listOf(
//            PostFile(type = MimeType.PDF.value),
//            PostFile(type = MimeType.VIDEO.value)
//        )
//    )
//    AppTheme {
//        FeedPostItem(post = post, onPostEvent = {}, currentUserID = "0000")
//    }
//}

