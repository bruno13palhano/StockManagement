package com.bruno13palhano

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory {
    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "shopdani.db")
    }
}