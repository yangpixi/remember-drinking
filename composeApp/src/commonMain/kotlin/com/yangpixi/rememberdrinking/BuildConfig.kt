package com.yangpixi.rememberdrinking

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * @author yangpixi
 * @date 2025/12/28 11:27
 * @description kmp项目无法像安卓原生开发一样使用BuildConfig设置，故手动创建一个config类
 */

object BuildConfig {
    val TOKEN = stringPreferencesKey("TOKEN_KEY")

    const val BASE_URL = "http://localhost:8080"
}