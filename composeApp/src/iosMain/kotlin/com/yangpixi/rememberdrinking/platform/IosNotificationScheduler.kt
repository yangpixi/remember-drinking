package com.yangpixi.rememberdrinking.platform

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.*
import kotlin.coroutines.resume

/**
 * @author yangpixi
 * @date 2026/1/12 14:13
 * @description ios平台通知实现,暂未实现固定时间段内提醒
 */

class IosNotificationScheduler : NotificationScheduler {
    override fun scheduleNotification(
        title: String,
        content: String,
        id: Int,
        delayMillis: Long
    ) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(content)
            setSound(UNNotificationSound.defaultSound)
        }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = (delayMillis / 1000.0).coerceAtLeast(1.0), // ios默认为秒，而非毫秒时间戳
            repeats = true // 可重复
        )

        val request = UNNotificationRequest.requestWithIdentifier(id.toString(), content, trigger)
        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { error ->
            if (error != null) println("iOS Notification Error: ${error.localizedDescription}")
        }
    }

    override suspend fun requestPermission(): Boolean = suspendCancellableCoroutine { cont ->
        val options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(options) { granted, error ->
            if (cont.isActive) {
                if (error != null) {
                    cont.resume(false)
                } else {
                    cont.resume(granted)
                }
            }
        }
    }
}