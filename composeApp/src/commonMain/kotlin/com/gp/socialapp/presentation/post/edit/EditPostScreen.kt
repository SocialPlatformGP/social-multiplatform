import androidx.compose.foundation.layout.Column
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
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.presentation.post.create.component.BottomOptionRow
import com.gp.socialapp.presentation.post.create.component.CreatePostTopBar
import com.gp.socialapp.presentation.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyBottomSheet
import com.gp.socialapp.presentation.post.create.component.MyExistingTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.MyTextField
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.presentation.post.create.uploadPostFiles
import com.gp.socialapp.presentation.post.edit.EditPostAction
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostUIState
import com.mohamedrejeb.calf.core.LocalPlatformContext
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
        LifecycleEffect(
            onStarted = { screenModel.init(post) },
            onDisposed = { screenModel.dispose() }
        )
        val state by screenModel.uiState.collectAsState()
        if (state.editSuccess) {
            navigator.pop()
        }
        MaterialTheme {
            EditPostContent(
                state = state,
                action = {
                    when (it) {
                        EditPostAction.NavigateBack -> navigator.pop()
                        else -> screenModel.handleAction(it)
                    }
                }

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPostContent(
    action: (EditPostAction) -> Unit,
    state: EditPostUIState,

    ) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var existingTagsDialogState by remember { mutableStateOf(false) }
    var newTagDialogState by remember { mutableStateOf(false) }
    val context = LocalPlatformContext.current
    var pickedFileType: FilePickerFileType by remember { mutableStateOf(FilePickerFileType.All) }
    val filePicker = rememberFilePickerLauncher(
        type = pickedFileType,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files ->
            uploadPostFiles(
                scope,
                files,
                context,
                { action(EditPostAction.OnFileAdded(it)) },
            )
            pickedFileType = FilePickerFileType.All
        }
    )
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = { action(EditPostAction.NavigateBack) },
                onPostClick = { action(EditPostAction.OnApplyEditClicked) },
                stringResource(Res.string.edit_post)
            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MyTextField(
                value = state.title,
                label = "Title",
                onValueChange = { newTitle ->
                    action(EditPostAction.OnTitleChanged(newTitle))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            )
            MyTextField(
                value = state.body,
                label = "Body",
                onValueChange = { newBody ->
                    action(EditPostAction.OnContentChanged(newBody))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            TagsRow(
                tags = state.postTags.toList(),
                onTagClick = { tag ->
                    action(EditPostAction.OnTagRemoved(tag))
                }
            )
            FilesRow(
                modifier = Modifier.fillMaxWidth(),
                state.postAttachments,
                onFileDelete = { file ->
                    action(EditPostAction.OnFileRemoved(file))
                }
            )
            HorizontalDivider()
            BottomOptionRow(
                modifier = Modifier.fillMaxWidth(),
                onAddFileClicked = {
                    filePicker.launch()
                },
                onAddImageClicked = {
                    pickedFileType = FilePickerFileType.Image
                    filePicker.launch()
                },
                onAddTagClicked = {
                    scope.launch { bottomSheetState.show() }.invokeOnCompletion {
                        if (bottomSheetState.isVisible) {
                            openBottomSheet = true
                        }
                    }
                },
                onAddVideoClicked = {
                    pickedFileType = FilePickerFileType.Video
                    filePicker.launch()
                },
                pickedFileType = state.postAttachments.firstOrNull()?.type ?: ""
            )
        }
        if (openBottomSheet) {
            MyBottomSheet(
                openBottomSheet = { value ->
                    openBottomSheet = value
                },
                bottomSheetState = bottomSheetState,
                existingTagsDialogState = { value ->
                    existingTagsDialogState = value
                },
                newTagDialogState = { value ->
                    newTagDialogState = value
                },
            )
        }
        if (existingTagsDialogState) {
            MyExistingTagAlertDialog(
                existingTagsDialogState = { value ->
                    existingTagsDialogState = value
                },
                channelTags = state.channelTags.toList(),
                selectedTags = {
                    action(EditPostAction.OnTagAdded(it))
                },
            )
        }
        if (newTagDialogState) {
            NewTagAlertDialog(
                newTagDialogState = { value ->
                    newTagDialogState = value
                },
                confirmNewTags = {
                    action(EditPostAction.OnTagAdded(it))
                },
            )

        }
    }
}







