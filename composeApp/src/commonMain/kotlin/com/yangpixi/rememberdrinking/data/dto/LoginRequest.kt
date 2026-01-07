package com.yangpixi.rememberdrinking.data.dto

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2026/1/7 21:58
 * @description 登录请求封装
 */

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
