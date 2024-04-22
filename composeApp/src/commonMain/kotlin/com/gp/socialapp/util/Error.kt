package com.gp.socialapp.util

sealed interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_INTERNET_OR_SERVER_DOWN,
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        METHOD_NOT_ALLOWED,
        TIMEOUT,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN,
        FILE_NOT_FOUND,
        FOLDER_NOT_DELETED,
        FILE_NOT_DELETED,
        CANT_UPLOAD_FILE,
        CANT_CREATE_FOLDER,
        CANT_GET_FILES_AT_PATH
    }

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