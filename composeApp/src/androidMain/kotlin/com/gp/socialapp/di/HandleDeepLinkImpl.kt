package com.gp.socialapp.di

import android.content.Intent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.handleDeeplinks

class HandleDeepLinkImpl(
    private val suba: SupabaseClient
) : HandleDeepLink {
    override fun handleDeepLink(intent: Intent) {
        suba.handleDeeplinks(intent)
    }
}