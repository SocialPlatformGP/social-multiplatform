import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.app.App
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
        ConfirmLeaveCommunityDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}