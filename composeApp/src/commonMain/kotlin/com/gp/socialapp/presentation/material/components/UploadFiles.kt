package com.gp.socialapp.presentation.material.components

import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getExtensionFromMimeType
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun uploadFiles(
    scope: CoroutineScope,
    files: List<KmpFile>,
    context: PlatformContext,
    action: (MaterialAction) -> Unit
) {
    scope.launch {
        files.firstOrNull()?.let { file ->
            val byteFile = file.readByteArray(context)
            val mimeType = MimeType.getMimeTypeFromFileName(file.getName(context) ?: "")
            val extension = getExtensionFromMimeType(mimeType)
            action(
                MaterialAction.OnUploadFileClicked(
                    file.getName(context) ?: "",
                    extension,
                    byteFile
                )
            )
        }
    }
}