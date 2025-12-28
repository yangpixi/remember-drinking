package com.yangpixi.rememberdrinking.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
}
