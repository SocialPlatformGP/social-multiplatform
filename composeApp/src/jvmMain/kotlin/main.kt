import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.theme.AppTheme
import com.mohamedrejeb.calf.picker.FilePickerFileType
import java.awt.Dimension

fun main() = application {
    Window(
        title = "SocialMultiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}

@Preview
@Composable
fun PreviewApp() {
    AppTheme {
        Column {

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
