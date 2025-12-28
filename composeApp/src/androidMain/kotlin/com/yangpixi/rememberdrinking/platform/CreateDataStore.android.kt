package com.yangpixi.rememberdrinking.platform

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * @author yangpixi
 * @date 2025/12/28 11:14
 * @description 安卓平台实现DataStore获取
 */

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)
