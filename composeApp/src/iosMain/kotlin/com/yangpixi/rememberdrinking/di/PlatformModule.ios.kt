package com.yangpixi.rememberdrinking.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.platform.IosNotificationScheduler
import com.yangpixi.rememberdrinking.platform.NotificationScheduler
import com.yangpixi.rememberdrinking.platform.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        createDataStore()
    }

    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema, "app.db")
    }

    single<NotificationScheduler> {
        IosNotificationScheduler()
    }
}