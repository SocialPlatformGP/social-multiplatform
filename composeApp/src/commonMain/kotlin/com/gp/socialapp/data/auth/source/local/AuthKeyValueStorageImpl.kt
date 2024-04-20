package com.gp.socialapp.data.auth.source.local

import com.gp.socialapp.util.AppConstants
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class AuthKeyValueStorageImpl(
    private val settings: Settings
) : AuthKeyValueStorage {
    override var token: String?
        get() = settings.getStringOrNull(AppConstants.StorageKeys.USER_TOKEN.key)
        set(value) {
            settings[AppConstants.StorageKeys.USER_TOKEN.key] = value
        }
    override var userId: String?
//        get() = settings.getStringOrNull(AppConstants.StorageKeys.USER_ID.key)
        get() = settings.getString(AppConstants.StorageKeys.USER_ID.key, "662106064d63fe2e5e09375d")
        set(value) {
            settings[AppConstants.StorageKeys.USER_ID.key] = value
        }

    override fun cleanStorage() {
        settings.clear()
    }
}