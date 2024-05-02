package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
        deep.handleDeepLink(intent)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun PreviewApp() {

}


