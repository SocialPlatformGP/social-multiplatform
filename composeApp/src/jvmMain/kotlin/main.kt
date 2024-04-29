import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.theme.AppTheme
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
//            CommunityMembersContent(
//                requests = requests,
//                admins = admin,
//                isAdmin = true,
//                onAction = {},
//                members = members
//            )
        }

    }
}

val requests = listOf<CommunityMemberRequest>(
    CommunityMemberRequest(
        communityId = "honestatis",
        userId = "cursus",
        userName = "Roosevelt Stevens",
        userAvatar = ""
    ),
    CommunityMemberRequest(
        communityId = "libero",
        userId = "placerat",
        userName = "Marion Pratt",
        userAvatar = ""
    )
)
val admin = listOf("chrystal.rollins@example.com")
val members = listOf<User>(
    User(
        id = "sollicitudin",
        firstName = "Lily",
        lastName = "Annabelle",
        profilePictureURL = "",
        email = "dana.bond@example.com",
        phoneNumber = "(945) 535-2335",
        birthdate = 6509,
        bio = "deseruisse",
        createdAt = 9466,
        isAdmin = false,
        isDataComplete = false
    ),
    User(
        id = "tantas",
        firstName = "Eva",
        lastName = "Farmer",
        profilePictureURL = "",
        email = "chrystal.rollins@example.com",
        phoneNumber = "(611) 956-1474",
        birthdate = 3674,
        bio = "latine",
        createdAt = 9319,
        isAdmin = false,
        isDataComplete = false
    ),
    User(
        id = "fabellas",
        firstName = "Elnora",
        lastName = "Vargas",
        profilePictureURL = "",
        email = "jimmie.ware@example.com",
        phoneNumber = "(150) 124-3902",
        birthdate = 8498,
        bio = "deseruisse",
        createdAt = 7285,
        isAdmin = false,
        isDataComplete = false
    )
)
