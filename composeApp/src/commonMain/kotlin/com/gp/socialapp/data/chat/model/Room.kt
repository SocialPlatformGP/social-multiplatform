package com.gp.socialapp.data.chat.model

import korlibs.time.DateTimeTz


data class Room(
    val id: Long = 0L,
    val name: String = "",
    val picUrl: String = "",
    val members: Map<String, Boolean> = emptyMap(), //userId to isAdmin
    val isPrivate: Boolean = true,
    val createdAt: DateTimeTz = DateTimeTz.nowLocal(),
    val bio: String = "", //group chat description
)