package com.gp.socialapp.presentation.auth.login.components.imagevectors

import androidx.compose.ui.graphics.vector.ImageVector
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Apple
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Discord
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Facebook
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Github
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Google
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Linkedin
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Microsoft
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Slack
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Twitter
import kotlin.collections.List as ____KtList

public object OAuthProviderIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val OAuthProviderIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Apple, Discord, Facebook, Github, Google, Linkedin, Microsoft, Slack,
        Twitter)
    return __AllIcons!!
  }
