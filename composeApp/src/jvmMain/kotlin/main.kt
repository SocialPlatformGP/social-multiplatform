import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.presentation.chat.chatroom.components.TriangleEdgeShape
import com.gp.socialapp.presentation.home.components.ConfirmLeaveCommunityDialog
import com.gp.socialapp.theme.AppTheme
import java.awt.Dimension
import java.awt.Window

lateinit var CURRENT_WINDOW: Window
fun main() = application {
    Window(
        title = "EduLink",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        CURRENT_WINDOW = window
        window.minimumSize = Dimension(350, 600)
        App()
    }
}
val currentSubmission = UserAssignmentSubmission(userName = "Ana de armas")
val submissions = listOf(
    UserAssignmentSubmission(userName = "Userer")
)
@Preview
@Composable
fun PreviewApp() {
    AppTheme {
        val community = Community(
            id = "1",
            name = "Test Community",
            description = "Test Description",
            members = mapOf("1" to true, "2" to false, "3" to false),
        )
        Row (
            modifier = Modifier.padding(50.dp),
            verticalAlignment = Alignment.Top
        ){
            Column (
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = TriangleEdgeShape(5, false)
                )
            ){  }
            Column (
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
                    ).size(width = 90.dp, height = 30.dp)
            ){}
        }
    }
}