package com.gp.socialapp.util

object AppConstants {
    enum class StorageKeys {
        USER_TOKEN,
        USER_ID,
        POST_LAST_UPDATED;
        val key get() = this.name
    }
}