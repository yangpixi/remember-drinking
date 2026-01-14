package com.yangpixi.rememberdrinking.data.dto

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2026/1/14 11:45
 * @description UserDTO类
 */

@Serializable
data class UserDTO(
    val username: String? = null,
    val phone: String? = null,
    val avatar: String? = null
)
