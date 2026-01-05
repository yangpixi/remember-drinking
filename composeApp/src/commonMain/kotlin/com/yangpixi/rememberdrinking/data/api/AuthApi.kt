package com.yangpixi.rememberdrinking.data.api

import com.yangpixi.rememberdrinking.data.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

/**
 * @author yangpixi
 * @date 2026/1/2 22:40
 * @description 验证（登录 + 注册）用api
 */

class AuthApi(
    private val client: HttpClient
) {
    suspend fun doRegister(data: RegisterRequest): Unit {
        client.post("/auth/user/register") {
            setBody(data)
        }
    }

    // TODO: 实现登录接口

}