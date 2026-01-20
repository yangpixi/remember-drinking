package com.yangpixi.rememberdrinking.domain.repository

import com.yangpixi.rememberdrinking.data.dto.RecordDTO

/**
 * @author yangpixi
 * @date 2026/1/20 12:49
 * @description 记录repo
 */

interface RecordRepo {
    suspend fun uploadRecord(records: RecordDTO)
}