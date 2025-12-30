package com.yangpixi.rememberdrinking.domain.model

/**
 * @author yangpixi
 * @date 2025/12/30 20:58
 * @description
 */

data class WaterRecord(
    val id: Long,
    val amountMl: Long,
    val recordTime: Long,
    val isDeleted: Boolean
)
