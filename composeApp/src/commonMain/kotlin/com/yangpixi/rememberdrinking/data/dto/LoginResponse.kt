package com.yangpixi.rememberdrinking.data.dto

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2026/1/7 22:35
 * @description 登录成功后的返回
 */

@Serializable
data class LoginResponse(
    val userId: String,
    val token: String
)