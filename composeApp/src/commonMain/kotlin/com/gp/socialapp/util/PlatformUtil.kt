package com.gp.socialapp.util

expect fun getPlatform(): Platform
enum class Platform {
    ANDROID, JVM, JS
}