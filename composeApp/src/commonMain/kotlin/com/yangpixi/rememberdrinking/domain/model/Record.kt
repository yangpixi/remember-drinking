package com.yangpixi.rememberdrinking.domain.model

/**
 * @author yangpixi
 * @date 2025/12/30 20:58
 * @description 数据表对应的实体类
 */

data class Record(
    val id: Long = 0L,
    val recordId: String,
    val amountMl: Long,
    val recordTime: Long,
    val isDeleted: Boolean = false,
    val isUploaded: Boolean = false
)
