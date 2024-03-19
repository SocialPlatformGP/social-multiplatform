package com.gp.socialapp.data.post.source.remote.model

@kotlinx.serialization.Serializable
data class PostFile(
    val file: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
) {
    companion object {
        fun PostFile.toDbString(): String {
            return "$url|$name|$type|$size"
        }

        fun String.toPostFile(): PostFile {
            val parts = this.split("|")
            require(parts.size == 4) { "Invalid string format for PostFile" }
            return PostFile(url = parts[0], name = parts[1], type = parts[2], size = parts[3].toLong())
        }
    }
}

