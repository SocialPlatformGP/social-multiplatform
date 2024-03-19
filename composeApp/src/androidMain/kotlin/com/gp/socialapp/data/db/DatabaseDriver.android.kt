package com.gp.socialapp.data.db

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gp.socialapp.db.AppDatabase
actual fun provideDbDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
//    return AndroidSqliteDriver(schema.synchronous(), context, "test.db")
    TODO()
}