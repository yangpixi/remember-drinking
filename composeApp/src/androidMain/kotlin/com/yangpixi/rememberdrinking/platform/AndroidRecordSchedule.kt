package com.yangpixi.rememberdrinking.platform

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import java.util.concurrent.TimeUnit

/**
 * @author yangpixi
 * @date 2026/1/20 15:15
 * @description
 */

class AndroidRecordSchedule(
    private val context: Context,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : RecordSchedule {
    override suspend fun doUploadRecordsWork() {
        globalSnackBarUtils.sendEvent("开始上传喝水记录")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<AndroidUploadRecordsWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                1000L,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "upload_record_work",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            workRequest
        )

        globalSnackBarUtils.sendEvent("上传完毕")
    }
}