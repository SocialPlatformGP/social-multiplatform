package com.gp.socialapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gp.socialapp.presentation.app.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

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
        setContent {
            App()
        }
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



