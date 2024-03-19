package com.gp.socialapp.data.post.source.remote.model

@kotlinx.serialization.Serializable
data class Tag(
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
) {
    companion object {
        fun Tag.toDbString(): String {
            return "$label|$intColor|$hexColor"
        }

        fun String.toTag(): Tag {
            val parts = this.split("|")
            require(parts.size == 3) { "Invalid string format for Tag" }
            return Tag(parts[0], parts[1].toInt(), parts[2])
        }
    }
}
