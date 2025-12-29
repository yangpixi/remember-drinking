package com.yangpixi.rememberdrinking.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.db.Water_intake_record
import com.yangpixi.rememberdrinking.presentation.screen.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author yangpixi
 * @date 2025/12/28 10:52
 * @description viewModel,repository等注入定义
 */

val commonModule = module {
    single(named("ApplicationScope")) {
        CoroutineScope(SupervisorJob( ) + Dispatchers.Default)
    }

    // home
    singleOf(::HomeViewModel)

    // 定义转换器
    val booleanAdapter = object : ColumnAdapter<Boolean, Long> {
        override fun decode(databaseValue: Long): Boolean = databaseValue == 1L
        override fun encode(value: Boolean): Long = if (value) 1L else 0L
    }
    // 完成Database的注入
    single {
        Database(get<SqlDriver>(), Water_intake_record.Adapter(booleanAdapter))
    }

}
