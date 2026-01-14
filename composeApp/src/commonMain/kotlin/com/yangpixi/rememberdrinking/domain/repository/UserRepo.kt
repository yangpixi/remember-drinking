package com.yangpixi.rememberdrinking.domain.repository


/**
 * @author yangpixi
 * @date 2026/1/14 11:48
 * @description UserRepo接口
 */

interface UserRepo {

    // 获取当前登录的用户数据
    suspend fun getCurrentUser()
}