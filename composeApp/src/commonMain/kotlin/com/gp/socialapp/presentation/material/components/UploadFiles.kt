package com.gp.socialapp.presentation.material.components

import com.gp.socialapp.presentation.material.MaterialAction
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
            action(
                MaterialAction.OnUploadFileClicked(
                    file.getName(context) ?: "",
                    "application/*",
                    byteFile
                )
            )
        }
    }
}