package com.gp.socialapp

import android.app.Application
import android.content.ClipboardManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.window.layout.WindowInfoTracker
import com.gp.socialapp.di.HandleDeepLink
import com.gp.socialapp.di.platformModule
import com.gp.socialapp.presentation.app.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.kodein.di.DI
import org.kodein.di.instance

class AndroidApp() : Application() {

    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Napier.base(DebugAntilog())
    }
}


class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val di = DI.lazy { import(platformModule) }.di
        val deep: HandleDeepLink by di.instance()
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        deep.handleDeepLink(intent)
        setContent {
            App()
        }
    }
    companion object {
        const val TAG = "AppActivity"
    }
}

@Preview
@Composable
fun PreviewApp() {


}


