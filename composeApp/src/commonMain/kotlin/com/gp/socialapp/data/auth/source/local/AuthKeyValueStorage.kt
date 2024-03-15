package com.gp.socialapp.data.auth.source.local

interface AuthKeyValueStorage {
    var token: String?
    var userId: String?
    fun cleanStorage()
}