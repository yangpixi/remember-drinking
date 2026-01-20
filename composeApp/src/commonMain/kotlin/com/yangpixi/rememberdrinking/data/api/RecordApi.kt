package com.yangpixi.rememberdrinking.data.api

import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * @author yangpixi
 * @date 2026/1/20 12:50
 * @description 记录api封装
 */

class RecordApi(
    private val client: HttpClient
) {
    suspend fun doUploadRecord(records: RecordDTO): HttpResponse {
        return client.post("record/upload") {
            contentType(ContentType.Application.Json)
            setBody(records)
        }
    }
}