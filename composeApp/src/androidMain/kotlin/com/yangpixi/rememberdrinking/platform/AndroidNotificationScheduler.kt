package com.yangpixi.rememberdrinking.platform

import android.content.Context
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * @author yangpixi
 * @date 2026/1/12 13:07
 * @description 实现workerRequest
 */

class AndroidNotificationScheduler(
    private val context: Context
) : NotificationScheduler {
    override fun scheduleNotification(
        title: String,
        content: String,
        id: Int,
        delayMillis: Long
    ) {
        val inputData = Data.Builder()
            .putString("title", title)
            .putString("content", content)
            .putInt("id", id)
            .build()
        
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = delayMillis,
            repeatIntervalTimeUnit = TimeUnit.MILLISECONDS
        )
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    override suspend fun requestPermission() : Boolean {
        return true
    }
}