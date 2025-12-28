package com.yangpixi.rememberdrinking.util

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
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
    val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        HttpResponseValidator {
            validateResponse { response ->
                val body = response.bodyAsText()
                val json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
                val code = json.decodeFromString<BaseResponse>(body)
                if (code.code == 401) {
                    authManager.removeToken()
                    throw ApiException(401, "用户未认证或登录已过期，请重新登录后再访问")
                }
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val token = authManager.getAccessToken() ?: ""
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

@Serializable
data class BaseResponse(
    val code: Int
)
