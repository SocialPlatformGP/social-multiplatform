package com.gp.socialapp.data.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect fun provideDbDriver(
    schema: SqlSchema<QueryResult.Value<Unit>>
): SqlDriver

const val DB_NAME = "edulink.db"
