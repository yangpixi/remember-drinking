package com.yangpixi.rememberdrinking.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.platform.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        createDataStore()
    }

    single {
        NativeSqliteDriver(Database.Schema, "app.db")
    }
}