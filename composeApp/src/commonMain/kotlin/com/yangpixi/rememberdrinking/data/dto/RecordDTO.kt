package com.yangpixi.rememberdrinking.data.dto

import kotlinx.serialization.Serializable

/**
 * @author yangpixi
 * @date 2026/1/20 14:21
 * @description 记录dto类
 */

@Serializable
data class RecordDTO(
    val recordList: List<RecordDataDTO>
)

@Serializable
data class RecordDataDTO(
    val recordId: String? = null,
    val amountMl: Long? = null,
    val recordTime: Long? = null,
    val isDeleted: Boolean? = null,
)


