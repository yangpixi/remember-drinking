package com.yangpixi.rememberdrinking.data.repository

import com.yangpixi.rememberdrinking.data.api.RecordApi
import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.data.mapper.toDomain
import com.yangpixi.rememberdrinking.domain.model.Record
import com.yangpixi.rememberdrinking.domain.repository.RecordRepo
import com.yangpixi.rememberdrinking.util.bodyOrThrow

/**
 * @author yangpixi
 * @date 2026/1/20 12:49
 * @description 实现类
 */

class RecordRepoImpl(
    private val recordApi: RecordApi,
    private val waterRepo: WaterRepo
) : RecordRepo {
    override suspend fun uploadRecord(records: List<RecordDTO>) {
        recordApi.doUploadRecord(records)
    }

    override suspend fun markAsUpload(records: List<Record>) {
        records.forEach {
            waterRepo.markUnUploadedRecord(it.id)
        }
    }

    override suspend fun getUserRecords(): Result<List<Record>> {
        return runCatching {
            val dtos = recordApi.fetchRecords().bodyOrThrow<List<RecordDTO>>()
            dtos.map {
                it.toDomain()
            }.toList()
        }
    }
}