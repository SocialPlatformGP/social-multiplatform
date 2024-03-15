package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gp.socialapp.data.post.source.remote.model.MimeType
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.di.appModules
import com.gp.socialapp.di.initKoin
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.presentation.post.feed.components.AttachmentItem
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.gp.socialapp.presentation.post.feed.components.TagItem
import com.gp.socialapp.presentation.post.feed.components.UserImage
import com.gp.socialapp.theme.AppTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Napier.base(DebugAntilog())
//        startKoin {
//            androidContext(this@AndroidApp)
//            androidLogger()
//            modules(appModules)
//        }

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

//@Preview
//@Composable
//fun PreviewApp() {
//    App()
//}

@Preview
@Composable
fun AttachmentItemPreview() {
    val post = Post(
        title = "Hello, My name is who? my name is what?",
        body = "My name is nigga nigga nigga slim shady",
        replyCount = 5,
        userName = "Eminem",
        publishedAt = "Tomorrow",
        votes = 15,
        attachments = listOf(PostFile(type = MimeType.PDF), PostFile(type = MimeType.VIDEO))
    )
    AppTheme {
        FeedPostItem(post = post, onPostEvent = {}, currentUserID = "0000")
    }
}

