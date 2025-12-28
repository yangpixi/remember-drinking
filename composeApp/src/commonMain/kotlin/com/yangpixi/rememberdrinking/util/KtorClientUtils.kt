package com.yangpixi.rememberdrinking.util

import com.yangpixi.rememberdrinking.BuildConfig
import com.yangpixi.rememberdrinking.data.api.ApiResponse
import com.yangpixi.rememberdrinking.data.api.BizException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * @author yangpixi
 * @date 2025/12/28 11:03
 * @description ktor配置
 */

fun getClient(
    authManager: AuthManager
) : HttpClient {
    // 提取公共json配置
    val jsonConfig = Json{
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    // 获取ktor客户端
    val client = HttpClient() {
        install(ContentNegotiation) {
            json(jsonConfig)
        }
        HttpResponseValidator {
            validateResponse { response ->
                val body = response.bodyAsText()
                val code = jsonConfig.decodeFromString<BaseResponse>(body)
                if (code.code == 401) {
                    authManager.removeToken()
                    throw BizException(401, "用户未认证或登录已过期，请重新登录后再访问")
                }
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val token = authManager.getToken() ?: ""
                    BearerTokens(token, "")
                }
            }
        }
        defaultRequest {
            url(BuildConfig.BASE_URL)
        }
    }
    return client
}

// 拓展HttpResponse类的方法，便于提取后端返回的数据
suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
    val data = this.body<ApiResponse<T>>()
    if (data.code == 0) {

        if (data.data != null) {
            return data.data
        }

        if (T::class == Unit::class) {
            return Unit as T
        }

        throw BizException(404, "服务器成功返回但是无数据")
    } else {
        throw BizException(data.code, data.msg)
    }
}

// 简化包装类，防止登录验证的时候直接转换类型导致报错
@Serializable
data class BaseResponse(
    val code: Int
)
