package com.gp.socialapp.data.material.source.local.model

import com.gp.socialapp.data.material.model.MaterialFile
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class FileEntity() : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var type: String = ""
    var url: String = ""
    var path: String = ""
    var localPath: String = ""

    constructor(
        name: String = "",
        type: String = "",
        url: String = "",
        id: String = "",
        path: String = "",
        localPath: String = ""
    ) : this() {
        this.name = name
        this.type = type
        this.url = url
        this.id = id
        this.path = path
        this.localPath = localPath
    }

    fun toMaterialFile(): MaterialFile {
        return MaterialFile(
            name = name,
            type = type,
            url = url,
            id = id,
            path = path,
            localPath = localPath
        )
    }

    companion object {
        fun MaterialFile.toEntity(): FileEntity {
            return FileEntity(
                name = this.name,
                type = this.type,
                url = this.url,
                id = this.id,
                path = this.path,
                localPath = this.localPath
            )
        }
    }
}

