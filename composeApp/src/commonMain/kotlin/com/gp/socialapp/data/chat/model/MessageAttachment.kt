package com.gp.socialapp.data.chat.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageAttachment(
    val byteArray: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)
