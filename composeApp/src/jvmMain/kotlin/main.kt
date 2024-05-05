import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.presentation.assignment.submissionreview.components.SubmissionReviewTopRow
import com.gp.socialapp.presentation.settings.components.SwitchSettingItem
import com.gp.socialapp.theme.AppTheme
import java.awt.Dimension

fun main() = application {
    Window(
        title = "EduLink",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
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
//            SubmissionReviewTopRow(
//                currentSubmission = currentSubmission,
//                submissions = submissions,
//                onNextClick = {},
//                onPreviousClick = {},
//                onSubmissionSelected = {},
//                onSubmitReviewClick = {}
//            )
        }

    }
}