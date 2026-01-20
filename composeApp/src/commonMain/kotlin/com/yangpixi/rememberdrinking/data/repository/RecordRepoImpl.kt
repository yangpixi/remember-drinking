package com.yangpixi.rememberdrinking.data.repository

import com.yangpixi.rememberdrinking.data.api.RecordApi
import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.domain.repository.RecordRepo

/**
 * @author yangpixi
 * @date 2026/1/20 12:49
 * @description 实现类
 */

class RecordRepoImpl(
    private val recordApi: RecordApi
) : RecordRepo {
    override suspend fun uploadRecord(records: RecordDTO) {
        recordApi.doUploadRecord(records)
    }
}