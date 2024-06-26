package com.gp.socialapp.data.material.model

data class MaterialItem(
    val communityId: String = "",
    val id: String = "",
    val path: String = "",
    val fileType: FileType = FileType.OTHER,
    val name: String = "",
    val createdBy: String = "",
    val fileUrl: String = "",
    val creationTime: String = "",
    val size: String = ""
)

enum class FileType {
    IMAGE,
    PDF,
    AUDIO,
    EXCEL,
    PPT,
    TEXT,
    WORD,
    ZIP,
    VIDEO,
    OTHER,
    FOLDER,
    UnKnown
}

