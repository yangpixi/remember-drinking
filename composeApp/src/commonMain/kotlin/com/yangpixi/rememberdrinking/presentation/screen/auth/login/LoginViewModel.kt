package com.yangpixi.rememberdrinking.presentation.screen.auth.login

import androidx.lifecycle.ViewModel
import com.yangpixi.rememberdrinking.data.api.AuthApi
import com.yangpixi.rememberdrinking.data.dto.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author yangpixi
 * @date 2026/1/4 12:51
 * @description viewModel
 */

class LoginViewModel(
    private val authApi: AuthApi
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


    suspend fun doRegister(username: String, password: String, phone: String) {
        val request: RegisterRequest = RegisterRequest(
            username,
            password,
            phone
        )
        authApi.doRegister(request);
    }
}