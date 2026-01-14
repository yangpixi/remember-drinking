package com.yangpixi.rememberdrinking.data.mapper

import com.yangpixi.rememberdrinking.data.dto.UserDTO
import com.yangpixi.rememberdrinking.domain.model.User

/**
 * @author yangpixi
 * @date 2026/1/14 11:46
 * @description UserMapper
 */

fun UserDTO.toDomain(): User {
    return User(
        username = this.username ?: "Unknown",
        phone = this.phone ?: "Unknown",
        avatar = this.avatar ?: "Unknown"
    )
}