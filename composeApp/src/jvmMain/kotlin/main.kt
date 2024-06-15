import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.presentation.material.MaterialUiState
import com.gp.socialapp.presentation.material.components.CreateFolderDialog
import com.gp.socialapp.presentation.material.components.MaterialScreenContent
import com.gp.socialapp.theme.AppTheme
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
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
        Column {

            CreateFolderDialog {

            }
        }
    }
}