package com.yangpixi.rememberdrinking.data.api

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2025/12/28 12:07
 * @description
 */

@Serializable
data class ApiResponse<T> (
    val code: Int? = null,
    val msg: String? = null,
    val data: T? = null
)