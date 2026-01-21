package com.yangpixi.rememberdrinking.domain.repository

import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.domain.model.WaterRecord

/**
 * @author yangpixi
 * @date 2026/1/20 12:49
 * @description 记录repo
 */

interface RecordRepo {
    suspend fun uploadRecord(records: RecordDTO)

    suspend fun markAsUpload(records: List<WaterRecord>)
}