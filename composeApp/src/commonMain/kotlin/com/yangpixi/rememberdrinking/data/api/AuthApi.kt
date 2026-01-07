package com.yangpixi.rememberdrinking.data.api

import com.yangpixi.rememberdrinking.data.dto.LoginRequest
import com.yangpixi.rememberdrinking.data.dto.LoginResponse
import com.yangpixi.rememberdrinking.data.dto.RegisterRequest
import com.yangpixi.rememberdrinking.util.bodyOrThrow
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

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
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    suspend fun doLogin(data: LoginRequest): Result<LoginResponse> {
        return runCatching {
            val response: HttpResponse = client.post("/auth/user/login") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }

            response.bodyOrThrow<LoginResponse>()
        }
    }
}