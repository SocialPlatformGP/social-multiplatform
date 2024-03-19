package com.gp.socialapp.data.db

import app.cash.sqldelight.async.coroutines.await
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import kotlinx.coroutines.withContext
import org.w3c.dom.Worker
import kotlin.js.Promise

actual class DatabaseDriver {
    actual suspend fun createDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver {
        return WebWorkerDriver(
                Worker(
                    js("""new URL("sqlite.worker.js", import.meta.url)""").unsafeCast<String>()
                )
            ).also {
                schema.awaitCreate(it)
            }
        }
}
