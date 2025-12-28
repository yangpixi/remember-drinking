package com.yangpixi.rememberdrinking.di

import com.yangpixi.rememberdrinking.platform.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        createDataStore()
    }
}