package com.yangpixi.rememberdrinking.platform

/**
 * @author yangpixi
 * @date 2026/1/12 12:48
 * @description 不同平台实现消息推送接口
 */

interface NotificationScheduler {
    fun scheduleNotification(title: String, content: String, id: Int, delayMillis: Long)

    suspend fun requestPermission() : Boolean
}