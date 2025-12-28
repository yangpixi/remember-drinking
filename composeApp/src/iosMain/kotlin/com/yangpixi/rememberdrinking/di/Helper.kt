package com.yangpixi.rememberdrinking.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

/**
 * @author yangpixi
 * @date 2025/12/28 10:59
 * @description 为ios原生提供koin注入方法
 */

fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(appModule())
    }
}