package com.yangpixi.rememberdrinking.data.dto

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2026/1/2 22:38
 * @description 注册请求包装
 */

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val phone: String
)
