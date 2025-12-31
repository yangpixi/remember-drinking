package com.yangpixi.rememberdrinking.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.presentation.screen.history.HistoryViewModel
import com.yangpixi.rememberdrinking.presentation.screen.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
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

    // homeViewModel获取
    viewModel{
        HomeViewModel( get<WaterRepo>(),get<DataStore<Preferences>>())
    }

    // 完成Database的注入
    single {
        Database(get<SqlDriver>())
    }

    // 获取单例WaterRepo
    singleOf(::WaterRepo)

    // historyViewModel
    viewModel {
        HistoryViewModel(get<WaterRepo>())
    }
}
