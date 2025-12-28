package com.yangpixi.rememberdrinking

import android.app.Application
import com.yangpixi.rememberdrinking.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author yangpixi
 * @date 2025/12/27 20:53
 * @description 主启动类
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule())
            androidLogger()
            androidContext(this@MainApplication)
        }
    }
}