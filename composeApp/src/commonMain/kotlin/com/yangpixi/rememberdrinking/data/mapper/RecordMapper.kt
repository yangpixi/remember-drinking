package com.yangpixi.rememberdrinking.data.mapper

import com.yangpixi.rememberdrinking.data.dto.RecordDTO
import com.yangpixi.rememberdrinking.domain.model.Record

/**
 * @author yangpixi
 * @date 2026/1/21 16:27
 * @description Record类对应的mapper
 */

fun RecordDTO.toDomain(): Record {
    return Record(
        id = 0, // 使用哨兵值
        recordId = this.recordId ?: "Unknown",
        amountMl = this.amountMl ?: 0,
        recordTime = this.recordTime ?: 0L,
        isDeleted = this.isDeleted ?: false,
        isUploaded = true
    )
}