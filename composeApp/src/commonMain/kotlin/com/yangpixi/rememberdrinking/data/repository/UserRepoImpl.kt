package com.yangpixi.rememberdrinking.data.repository

import com.yangpixi.rememberdrinking.data.api.UserApi
import com.yangpixi.rememberdrinking.data.dto.UserDTO
import com.yangpixi.rememberdrinking.data.mapper.toDomain
import com.yangpixi.rememberdrinking.domain.model.User
import com.yangpixi.rememberdrinking.domain.repository.UserRepo
import com.yangpixi.rememberdrinking.util.bodyOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author yangpixi
 * @date 2026/1/14 11:49
 * @description
 */

class UserRepoImpl(
    private val userApi: UserApi
) : UserRepo {

    private val _currentUser = MutableStateFlow<User?>(null)

    val currentUser: StateFlow<User?> = _currentUser

    override suspend fun getCurrentUser() {
        val userDTO = userApi.fetchUserDetails().bodyOrThrow<UserDTO>()
        val user = userDTO.toDomain()
        _currentUser.value = user
    }
}