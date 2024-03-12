package com.gp.socialapp.util

object AppConstants {
    enum class StorageKeys{
        USER_TOKEN;
        val key get() = this.name
    }
}