package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gp.socialapp.presentation.app.App
import com.gp.socialapp.util.CommonInjector
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.jan.supabase.gotrue.handleDeeplinks

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
        Napier.e("onCreate" + "${CommonInjector.suba}")
        CommonInjector.getSuba().handleDeeplinks(intent)
        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()

    }

}

