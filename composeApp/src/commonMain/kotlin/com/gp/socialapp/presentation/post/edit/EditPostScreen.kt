import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyBottomSheet
import com.gp.socialapp.presentation.post.create.component.MyExistingTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.edit_post

class EditPostScreen(val post: Post) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<EditPostScreenModel>()
        val state by screenModel.post.collectAsState()
        val existingTags by screenModel.channelTags.collectAsState()
        val success by screenModel.success.collectAsState()
        screenModel.setPost(post)
        if (success) {
            navigator.pop()
        }
        MaterialTheme {
            EditPostContent(
                state = state,
                channelTags = existingTags,
                onBackClick = { navigator.pop() },
                onPostClick = { screenModel.updatePost() },
                onTitleChange = { screenModel.updateTitle(it) },
                onBodyChange = { screenModel.updateBody(it) },
                confirmNewTags = {
                    screenModel.addTag(it.toList())
                },
                onAddImage = { file ->
                    screenModel.addFile(listOf(file))
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun EditPostContent(
    state: Post,
    channelTags: List<Tag>,
    onBackClick: () -> Unit,
    onPostClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    confirmNewTags: (Set<Tag>) -> Unit,
    onAddImage: (PostAttachment) -> Unit
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var existingTagsDialogState by remember { mutableStateOf(false) }
    var newTagDialogState by remember { mutableStateOf(false) }
    var selectedTags: List<Tag> by remember { mutableStateOf(emptyList()) }
    val context = LocalPlatformContext.current
    val imagePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    val image = file.readByteArray(context)
                    onAddImage(
                        PostAttachment(
                            file = image,
                            name = file.getName(context) ?: "",
                            type = FilePickerFileType.Image.toString(),
                            size = image.size.toLong()
                        )
                    )
                }
            }
        }
    )
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = onBackClick,
                onPostClick = onPostClick,
                stringResource(Res.string.edit_post)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MyTextField(
                value = state.title,
                label = "Title",
                onValueChange = { onTitleChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            )
            MyTextField(
                value = state.body,
                label = "Body",
                onValueChange = { onBodyChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            TagsRow(
                tags = selectedTags.toSet().toList(),
                onTagClick = { tag ->
                    println("Tag clicked: $tag")
                    println("Selected tags: $selectedTags")
                    selectedTags -= tag
                }
            )
            FilesRow(
                modifier = Modifier.fillMaxWidth(),
                state.attachments,
                onFileDelete = { file ->
                    println("File clicked: $file")
                }
            )
            HorizontalDivider()
            BottomOptionRow(
                onAddFileClicked = { /**/ },
                onAddImageClicked = {
                    imagePicker.launch()

                },
                onAddTagClicked = {
                    scope.launch { bottomSheetState.show() }.invokeOnCompletion {
                        if (bottomSheetState.isVisible) {
                            openBottomSheet = true
                        }
                    }
                },
                onAddVideoClicked = {/*TODO: Add video picker*/ },
                pickedFileType = state.attachments.firstOrNull()?.type ?: ""
            )
        }
        if (openBottomSheet) {
            MyBottomSheet(
                openBottomSheet = {
                    openBottomSheet = it
                },
                bottomSheetState = bottomSheetState,
                existingTagsDialogState = {
                    existingTagsDialogState = it
                },
                newTagDialogState = {
                    newTagDialogState = it
                },
            )
        }
        if (existingTagsDialogState) {
            MyExistingTagAlertDialog(
                existingTagsDialogState = {
                    existingTagsDialogState = it
                },
                channelTags = channelTags,
                selectedTags = {
                    selectedTags += it
                },

                )
        }
        if (newTagDialogState) {
            NewTagAlertDialog(
                newTagDialogState = {
                    newTagDialogState = it
                },
                confirmNewTags = {
                    confirmNewTags(it)
                },
            )

        }
    }
}







