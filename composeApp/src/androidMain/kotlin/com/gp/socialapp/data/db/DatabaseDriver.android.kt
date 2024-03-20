package com.gp.socialapp.data.db

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gp.socialapp.AndroidApp

actual fun provideDbDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
    val context: Context = AndroidApp.INSTANCE.applicationContext
    return AndroidSqliteDriver(schema, context, DB_NAME)
}