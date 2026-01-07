package com.yangpixi.rememberdrinking.presentation.screen.auth.login

import androidx.lifecycle.ViewModel
import com.yangpixi.rememberdrinking.data.api.AuthApi
import com.yangpixi.rememberdrinking.data.dto.LoginRequest
import com.yangpixi.rememberdrinking.data.dto.RegisterRequest
import com.yangpixi.rememberdrinking.util.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author yangpixi
 * @date 2026/1/4 12:51
 * @description viewModel
 */

class LoginViewModel(
    private val authApi: AuthApi,
    private val authManager: AuthManager
) : ViewModel() {

    private val _usernameValue = MutableStateFlow("")

    val usernameValue: StateFlow<String> = _usernameValue

    private val _passwordValue = MutableStateFlow("")

    val passwordValue = _passwordValue

    fun updateUsernameValue(username: String) {
        _usernameValue.value = username
    }

    fun updatePasswordValue(password: String) {
        _passwordValue.value = password
    }


    suspend fun doLogin(username: String, password: String) {
        val request = LoginRequest(
            username,
            password
        )
        try {
            val response = authApi.doLogin(request).getOrThrow() // 若是没有返回直接抛出异常
            authManager.saveToken(response.token) // 将token存储到dataStore里面
        } catch (e: Exception) {
            println(e.message)
        }
    }

}