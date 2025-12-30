package com.yangpixi.rememberdrinking.di

import app.cash.sqldelight.db.SqlDriver
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.db.Database
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
    single{
        HomeViewModel(get<WaterRepo>())
    }

    // 完成Database的注入
    single {
        Database(get<SqlDriver>())
    }

    // 获取单例WaterRepo
    singleOf(::WaterRepo)

}
