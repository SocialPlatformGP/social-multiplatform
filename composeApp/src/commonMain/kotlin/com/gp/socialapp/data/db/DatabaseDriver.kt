package com.gp.socialapp.data.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class DatabaseDriver {
    fun createDriver(): SqlDriver
}
const val DB_NAME = "edulink.db"
