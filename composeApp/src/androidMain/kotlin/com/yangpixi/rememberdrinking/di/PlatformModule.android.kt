package com.yangpixi.rememberdrinking.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.platform.AndroidNotificationScheduler
import com.yangpixi.rememberdrinking.platform.AndroidRecordSchedule
import com.yangpixi.rememberdrinking.platform.AndroidUploadRecordsWorker
import com.yangpixi.rememberdrinking.platform.NotificationScheduler
import com.yangpixi.rememberdrinking.platform.RecordSchedule
import com.yangpixi.rememberdrinking.platform.createDataStore
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        createDataStore(get<Context>())
    }

    single<SqlDriver> {
        AndroidSqliteDriver(Database.Schema, get<Context>(), "app.db")
    }

    single<NotificationScheduler> {
        AndroidNotificationScheduler(get<Context>())
    }

    workerOf(::AndroidUploadRecordsWorker)
    singleOf(::AndroidRecordSchedule) {
        bind<RecordSchedule>()
    }
}
