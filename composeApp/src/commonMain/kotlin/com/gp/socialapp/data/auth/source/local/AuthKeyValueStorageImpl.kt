package com.gp.socialapp.data.auth.source.local

import com.gp.socialapp.util.AppConstants
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class AuthKeyValueStorageImpl: AuthKeyValueStorage{
    private val settings: Settings by lazy {Settings()}
    override var token: String?
        get() = settings.getStringOrNull(AppConstants.StorageKeys.USER_TOKEN.key)
        set(value) {
            settings[AppConstants.StorageKeys.USER_TOKEN.key] = value
        }
    override fun cleanStorage() {
        settings.clear()
    }
}