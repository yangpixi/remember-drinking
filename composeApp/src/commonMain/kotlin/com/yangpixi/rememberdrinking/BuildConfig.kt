package com.yangpixi.rememberdrinking

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * @author yangpixi
 * @date 2025/12/28 11:27
 * @description kmp项目无法像安卓原生开发一样使用BuildConfig设置，故手动创建一个config类
 */

object BuildConfig {
    val TOKEN = stringPreferencesKey("TOKEN_KEY")

    const val BASE_URL = "http://192.168.10.221:8080"

    val GOAL_KEY = intPreferencesKey("GOAL_KEY")

    val NOTIFY_IS_ENABLED = booleanPreferencesKey("notificationState")

    val NOTIFY_DELAY_TIME = longPreferencesKey("delayTime")

    val NOTIFY_CONTENT = stringPreferencesKey("notificationContent")
}