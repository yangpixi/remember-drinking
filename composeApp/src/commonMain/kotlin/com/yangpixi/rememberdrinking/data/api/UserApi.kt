package com.yangpixi.rememberdrinking.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters

/**
 * @author yangpixi
 * @date 2026/1/14 15:08
 * @description 用户相关api
 */

class UserApi(
    private val client: HttpClient
) {

    suspend fun fetchUserDetails(): HttpResponse {
        return client.get("/user/details")
    }

    suspend fun updateAvatar(fileBytes: ByteArray, fileName: String) {
        client.submitFormWithBinaryData(
            url = "user/avatar/change",
            formData = formData {
                append("file", fileBytes, headers = Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                })
            }
        )
    }

    suspend fun updatePhone(phone: String) {
        client.submitForm(
            url = "user/phone/change",
            formParameters = parameters {
                append("phone", phone)
            }
        )
    }

    suspend fun updatePassword(password: String) {
        client.submitForm(
            url = "user/password/change",
            formParameters = parameters {
                append("password", password)
            }
        )
    }
}