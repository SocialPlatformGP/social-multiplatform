package com.gp.socialapp.util

import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.ExternalAuthAction
import io.github.jan.supabase.gotrue.FlowType
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.singleton

object CommonInjector {
    val suba = DI.Module("suba") {
        bind<SupabaseClient>() with singleton {
            Napier.e("onCreate" + "${this}")
            createSupabaseClient(
                supabaseUrl = "https://vszvbwfzewqeoxxpetgj.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZzenZid2Z6ZXdxZW94eHBldGdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM2MjM0MjUsImV4cCI6MjAyOTE5OTQyNX0.dO4SiJ9MCN0gZaY15kjqRdYL0NRFTZWID_xiYWhAnk8"
            ) {
                install(Auth) {
                    flowType = FlowType.PKCE
                    enableLifecycleCallbacks = true
                    host = "login"
                    scheme = "com.gp.edulink"
                    defaultExternalAuthAction = ExternalAuthAction.CustomTabs()
                }
            }
        }
    }

    fun getSuba() = DI.lazy { import(suba) }.direct.instance<SupabaseClient>()
}
