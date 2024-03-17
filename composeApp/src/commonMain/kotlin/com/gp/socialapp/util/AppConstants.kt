package com.gp.socialapp.util

object AppConstants {
    enum class StorageKeys {
        USER_TOKEN,
        USER_ID;

        val key get() = this.name
    }
}