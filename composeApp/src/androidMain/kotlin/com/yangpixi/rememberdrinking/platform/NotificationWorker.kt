package com.yangpixi.rememberdrinking.platform

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yangpixi.rememberdrinking.R

/**
 * @author yangpixi
 * @date 2026/1/12 12:51
 * @description 实现后台发送通知的worker
 */

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Reminder"
        val content = inputData.getString("content") ?: "Remember to drink water!"
        val id = inputData.getInt("id", 0)

        try {
            val builder = NotificationCompat.Builder(applicationContext, "drink_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            val manager = NotificationManagerCompat.from(applicationContext)

            manager.notify(id, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
            return Result.failure()
        }
        return Result.success()
    }

}