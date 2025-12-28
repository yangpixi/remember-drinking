package com.yangpixi.rememberdrinking.di

import org.koin.core.module.Module

/**
 * @author yangpixi
 * @date 2025/12/28 10:53
 * @description 平台差异化注入方法
 */

// 使用expect/actual逻辑实现不同平台注入
expect val platformModule: Module