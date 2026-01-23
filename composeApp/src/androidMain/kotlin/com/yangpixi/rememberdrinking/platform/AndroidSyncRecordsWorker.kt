package com.yangpixi.rememberdrinking.platform

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.domain.repository.RecordRepo
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils

/**
 * @author yangpixi
 * @date 2026/1/19 16:37
 * @description 上传喝水记录的worker
 */

class AndroidSyncRecordsWorker(
    context: Context,
    params: WorkerParameters,
    private val waterRepo: WaterRepo,
    private val recordRepo: RecordRepo,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        val records = waterRepo.getUnUploadedRecords()
        val recordList = records.map { record ->
            RecordDTO(
                recordId = record.recordId,
                amountMl = record.amountMl,
                recordTime = record.recordTime,
                isDeleted = record.isDeleted
            )
        }.toList()

        try {
            recordRepo.uploadRecord(recordList)
            recordRepo.markAsUpload(records)
        } catch (e: Exception) {
            Log.e("上传失败", e.message ?: "")
            globalSnackBarUtils.sendEvent("同步失败，请稍后重试")
            return Result.retry()
        }

        try {
            val records = recordRepo.getUserRecords().getOrThrow()
            waterRepo.insertOrUpdateRecord(records)
        } catch (e: Exception) {
            globalSnackBarUtils.sendEvent("同步失败，请稍后再试")
            return Result.retry()
        }

        return Result.success()
    }

}