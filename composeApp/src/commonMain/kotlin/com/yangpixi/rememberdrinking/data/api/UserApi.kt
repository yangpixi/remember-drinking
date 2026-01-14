package com.yangpixi.rememberdrinking.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

/**
 * @author yangpixi
 * @date 2026/1/14 15:08
 * @description
 */

class UserApi(
    private val client: HttpClient
) {

    suspend fun fetchUserDetails(): HttpResponse {
        return client.get("/user/details")
    }
}