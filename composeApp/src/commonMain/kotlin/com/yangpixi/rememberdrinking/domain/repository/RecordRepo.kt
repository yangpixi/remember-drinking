package com.yangpixi.rememberdrinking.domain.repository

import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.domain.model.Record

/**
 * @author yangpixi
 * @date 2026/1/20 12:49
 * @description 记录repo
 */

interface RecordRepo {
    suspend fun uploadRecord(records: List<RecordDTO>)

    suspend fun markAsUpload(records: List<Record>)

    suspend fun getUserRecords(): Result<List<Record>>
}