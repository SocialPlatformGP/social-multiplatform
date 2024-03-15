package com.gp.socialapp

import android.app.Activity
import android.content.Intent

class FileHelper(private val activity: Activity) {

    private val filePickerRequestCode = 123 // Choose any request code you like

    fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // You can specify the MIME type of files you want to select here
        }
        activity.startActivityForResult(intent, filePickerRequestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == filePickerRequestCode && resultCode == Activity.RESULT_OK) {
            // Handle the file selection here
            val selectedFileUri = data?.data
            // Do something with the selected file URI
        }
    }
}

