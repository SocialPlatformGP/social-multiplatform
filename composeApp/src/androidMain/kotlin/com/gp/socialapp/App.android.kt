package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gp.socialapp.presentation.app.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.ExternalAuthAction
import io.github.jan.supabase.gotrue.handleDeeplinks

class AndroidApp() : Application() {
//    override val di = DI.lazy {
//        appModuleK
//        bind<Context>() with singleton { applicationContext }
//    }

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
        val supabaseClient = createSupabaseClient(
            supabaseUrl = "https://vszvbwfzewqeoxxpetgj.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZzenZid2Z6ZXdxZW94eHBldGdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM2MjM0MjUsImV4cCI6MjAyOTE5OTQyNX0.dO4SiJ9MCN0gZaY15kjqRdYL0NRFTZWID_xiYWhAnk8"
        ) {
            install(Auth) {
                host = "login"
                scheme = "com.gp.edulink"
                defaultExternalAuthAction = ExternalAuthAction.CustomTabs()
            }
        }
        supabaseClient.handleDeeplinks(intent)
        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("seerde", "action: ${intent.action}, data: ${intent.data}")
    }
}
//@Preview
//@Composable
//fun PreviewApp() {
//    AppTheme {
//        FileDetails(
//            MaterialFile(
//                "data",
//                "file",
//                "url",
//            )
//        )
//
//    }
//}



