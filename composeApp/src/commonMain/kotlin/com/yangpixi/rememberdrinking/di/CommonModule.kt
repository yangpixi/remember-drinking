package com.yangpixi.rememberdrinking.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import com.yangpixi.rememberdrinking.data.api.AuthApi
import com.yangpixi.rememberdrinking.data.api.UserApi
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.presentation.screen.auth.login.LoginViewModel
import com.yangpixi.rememberdrinking.presentation.screen.auth.register.RegisterViewModel
import com.yangpixi.rememberdrinking.presentation.screen.history.HistoryViewModel
import com.yangpixi.rememberdrinking.presentation.screen.home.HomeViewModel
import com.yangpixi.rememberdrinking.presentation.screen.profile.ProfileViewModel
import com.yangpixi.rememberdrinking.presentation.screen.settings.SettingsViewModel
import com.yangpixi.rememberdrinking.util.AuthManager
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import com.yangpixi.rememberdrinking.util.getClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author yangpixi
 * @date 2025/12/28 10:52
 * @description viewModel,repository等注入定义
 */

val commonModule = module {
    single(named("ApplicationScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    // homeViewModel获取
    viewModel {
        HomeViewModel(get<WaterRepo>(), get<DataStore<Preferences>>())
    }

    // 完成Database的注入
    single {
        Database(get<SqlDriver>())
    }

    // 获取认证管理器示例对象
    single {
        AuthManager(get<DataStore<Preferences>>(), get(named("ApplicationScope")))
    }

    // 获取ktor客户端
    single {
        getClient(get<AuthManager>())
    }

    // 获取单例WaterRepo
    singleOf(::WaterRepo)

    // historyViewModel
    viewModel {
        HistoryViewModel(get<WaterRepo>())
    }

    // 获取单例AuthApi对象
    singleOf(::AuthApi)

    // loginViewModel
    singleOf(::LoginViewModel)

    // 获取全局snackbar工具
    singleOf(::GlobalSnackBarUtils)

    // 注册界面viewModel
    viewModel {
        RegisterViewModel(get(), get())
    }

    // 获取userRepo相关
    singleOf(::UserApi)
    singleOf(::UserRepoImpl)

    // 设置viewModel
    viewModelOf(::SettingsViewModel)

    // 资料界面Profile
    viewModelOf(::ProfileViewModel)
}
