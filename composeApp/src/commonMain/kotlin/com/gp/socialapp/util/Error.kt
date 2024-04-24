package com.gp.socialapp.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Error

@Serializable
sealed interface DataError : Error {
    @Serializable
    enum class Network(val userMessage: String) : DataError {
        NO_INTERNET_OR_SERVER_DOWN("No internet connection or server down"),
        BAD_REQUEST("Bad request"),
        UNAUTHORIZED("Unauthorized"),
        FORBIDDEN("Forbidden"),
        NOT_FOUND("Not found"),
        METHOD_NOT_ALLOWED("Method not allowed"),
        TIMEOUT("Timeout"),
        PAYLOAD_TOO_LARGE("Payload too large"),
        SERVER_ERROR("Server error"),
        SERIALIZATION_ERROR("Serialization error"),
        UNKNOWN("Unknown"),
        FILE_NOT_FOUND("File not found"),
        FOLDER_NOT_DELETED("Folder not deleted"),
        FILE_NOT_DELETED("File not deleted"),
        CANT_UPLOAD_FILE("Can't upload file"),
        CANT_CREATE_FOLDER("Can't create folder"),
        CANT_GET_FILES_AT_PATH("Can't get files at path"),
        FILE_ALREADY_EXISTS("File already exists"),
        FAILED_TO_SERIALIZE_THE_REQUEST("Failed to serialize the request")
    }

    @Serializable
    enum class Local : DataError {
        FILE_NOT_FOUND,
        FILE_ALREADY_EXISTS,
        FILE_NOT_CREATED,
        FILE_NOT_DELETED,
        FILE_NOT_UPDATED,
        FILE_NOT_DOWNLOADED,
        FILE_NOT_OPENED,
        FILE_NOT_SHARED,
        UNKNOWN
    }
}

