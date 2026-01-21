package com.yangpixi.rememberdrinking.platform

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.data.dto.RecordDataDTO
import com.yangpixi.rememberdrinking.data.repository.RecordRepoImpl
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils

/**
 * @author yangpixi
 * @date 2026/1/19 16:37
 * @description 上传喝水记录的worker
 */

class AndroidUploadRecordsWorker(
    context: Context,
    params: WorkerParameters,
    private val waterRepo: WaterRepo,
    private val recordRepo: RecordRepoImpl,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
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
            return Result.success()
        }

        try {
            recordRepo.uploadRecord(RecordDTO(recordList))
            recordRepo.markAsUpload(records)
        } catch (e: Exception) {
            Log.e("上传失败", e.message ?: "")
            globalSnackBarUtils.sendEvent("上传失败，请稍后重试")
            return Result.retry()
        }
        return Result.success()
    }

}