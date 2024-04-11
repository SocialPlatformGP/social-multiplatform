package com.gp.socialapp.data.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.gp.socialapp.db.AppDatabase
import com.gp.socialapp.util.AppConstants.DB_NAME


fun provideDbDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
    return JdbcSqliteDriver("jdbc:sqlite:${DB_NAME}")
        .also { driver ->
            AppDatabase.Schema.create(driver)
        }
}