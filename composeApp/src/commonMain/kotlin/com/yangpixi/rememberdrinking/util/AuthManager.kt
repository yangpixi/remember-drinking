package com.yangpixi.rememberdrinking.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.yangpixi.rememberdrinking.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * @author yangpixi
 * @date 2025/12/28 11:07
 * @description 登录管理器
 */

class AuthManager(
    private val dataStore: DataStore<Preferences>,
    private val applicationScope: CoroutineScope
) {
    // 使用密封接口，管理登录状态
    sealed interface AuthStatus {
        object Loading : AuthStatus
        data class Authenticated(val token: String) : AuthStatus
        object Unauthenticated : AuthStatus
    }

    // 移除登录token
    suspend fun removeToken(): Unit {
        dataStore.edit { preferences ->
            preferences.remove(BuildConfig.TOKEN)
        }
    }

    // 保存登录token
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[BuildConfig.TOKEN] = token
        }
    }

    // 获取登录token
    fun getToken(): String? {
        val status = authStatus.value
        if (status is AuthStatus.Authenticated) {
            return status.token
        } else {
            return null
        }
    }

    // 将登录状态转换为热流，方便各组件检测登录状态
    val authStatus: StateFlow<AuthStatus> = dataStore.data.map { preferences ->
        val token = preferences[BuildConfig.TOKEN]
        if (token.isNullOrBlank()) {
            AuthStatus.Unauthenticated
        } else {
            AuthStatus.Authenticated(token)
        }
    }.stateIn(
        scope = applicationScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthStatus.Loading
    )
}
