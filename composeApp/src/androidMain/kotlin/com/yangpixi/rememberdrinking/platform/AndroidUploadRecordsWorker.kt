package com.yangpixi.rememberdrinking.platform

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.data.dto.RecordDataDTO
import com.yangpixi.rememberdrinking.data.repository.RecordRepoImpl
import com.yangpixi.rememberdrinking.data.repository.WaterRepo

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
        } catch (e: Exception) {
            Log.e("上传失败", e.message ?: "")
            return Result.retry()
        }
        return Result.success()
    }

}