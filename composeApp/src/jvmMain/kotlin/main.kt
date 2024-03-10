import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.di.appModules
import java.awt.Dimension
import com.gp.socialapp.presentation.app.App
import org.koin.core.context.startKoin

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
    App()
}