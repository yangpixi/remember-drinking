package com.yangpixi.rememberdrinking.presentation.screen.auth.register

import androidx.lifecycle.ViewModel
import com.yangpixi.rememberdrinking.data.api.AuthApi
import com.yangpixi.rememberdrinking.data.dto.RegisterRequest
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author yangpixi
 * @date 2026/1/7 22:47
 * @description viewModel
 */

class RegisterViewModel(
    private val authApi: AuthApi,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : ViewModel() {

    private val _usernameValue = MutableStateFlow("")

    val usernameValue: StateFlow<String> = _usernameValue

    private val _passwordValue = MutableStateFlow("")

    val passwordValue: StateFlow<String> = _passwordValue

    private val _phoneValue = MutableStateFlow("")

    val phoneValue: StateFlow<String> = _phoneValue

    fun updateUsername(username: String) {
        _usernameValue.value = username
    }

    fun updatePassword(password: String) {
        _passwordValue.value = password
    }

    fun updatePhone(phone: String) {
        _phoneValue.value = phone
    }

    suspend fun doRegister(username: String, password: String, phone: String) {
        if (username.isNotBlank() && password.isNotBlank() && phone.isNotBlank()) {
            val request = RegisterRequest(
                username,
                password,
                phone
            )
            try {
                authApi.doRegister(request)
                globalSnackBarUtils.sendEvent("注册成功")
            } catch (e: Exception) {
                println(e.message)
                globalSnackBarUtils.sendEvent("注册失败")
            }
        } else {
            globalSnackBarUtils.sendEvent("请输入必要的内容")
        }
    }

}