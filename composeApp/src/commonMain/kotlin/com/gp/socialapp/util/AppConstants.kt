package com.gp.socialapp.util

object AppConstants {
    enum class StorageKeys {
        USER_TOKEN,
        USER_ID,
        POST_LAST_UPDATED,
        APP_THEME,
        RECENT_SEARCHES;

        val key get() = this.name
    }

    val DB_NAME = "edulink.db"
    const val BASE_URL = "http://192.168.1.4:8080/"
    const val SOCKET_URL = "ws://192.168.1.4:8080/"
}