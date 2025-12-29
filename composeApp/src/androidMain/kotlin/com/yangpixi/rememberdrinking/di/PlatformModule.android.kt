package com.yangpixi.rememberdrinking.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.platform.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        createDataStore(::get)
    }

    single {
        AndroidSqliteDriver(Database.Schema, get<Context>(), "app.db")
    }
}
