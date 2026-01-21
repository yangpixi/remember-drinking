package com.yangpixi.rememberdrinking.platform

import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.data.dto.RecordDataDTO
import com.yangpixi.rememberdrinking.data.repository.RecordRepoImpl
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import platform.UIKit.UIApplication
import platform.UIKit.UIBackgroundTaskIdentifier
import platform.UIKit.UIBackgroundTaskInvalid

/**
 * @author yangpixi
 * @date 2026/1/21 11:19
 * @description ios平台上传记录schedule
 */

class IosRecordSchedule(
    private val waterRepo: WaterRepo,
    private val recordRepo: RecordRepoImpl,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : RecordSchedule {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override suspend fun doUploadRecordsWork() {
        var taskId: UIBackgroundTaskIdentifier = UIBackgroundTaskInvalid

        // 向 iOS 系统注册
        taskId = UIApplication.sharedApplication.beginBackgroundTaskWithExpirationHandler {
            UIApplication.sharedApplication.endBackgroundTask(taskId)
            taskId = UIBackgroundTaskInvalid
        }

        scope.launch {
            val records = waterRepo.getUnUploadedRecords()
            val recordList = records.map { record ->
                RecordDataDTO(
                    recordId = record.recordId,
                    amountMl = record.amountMl,
                    recordTime = record.recordTime,
                    isDeleted = record.isDeleted
                )
            }.toList()

            if (recordList.isEmpty()) {
                globalSnackBarUtils.sendEvent("上传完毕")
                return@launch
            }

            try {
                recordRepo.uploadRecord(RecordDTO(recordList))
                recordRepo.markAsUpload(records)
                globalSnackBarUtils.sendEvent("上传完毕")
            } catch (e: Exception) {
                globalSnackBarUtils.sendEvent("上传失败，请稍后重试")
            }
        }
    }
}