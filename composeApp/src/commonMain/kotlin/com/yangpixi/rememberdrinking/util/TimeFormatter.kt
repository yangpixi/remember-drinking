package com.yangpixi.rememberdrinking.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * @author yangpixi
 * @date 2025/12/31 14:38
 * @description 时间转换工具
 */

val formatter = LocalDateTime.Format {
    year(); char('-')
    monthNumber(); char('-')
    day()

    chars("  ")

    hour(); char(':')
    minute()

}

@OptIn(ExperimentalTime::class)
fun parseToDateTime(millisSeconds: Long): String {
    val timeZone = TimeZone.currentSystemDefault()
    val instant = Instant.fromEpochMilliseconds(millisSeconds)
    val localDateTime = instant.toLocalDateTime(timeZone).format(formatter)
    return localDateTime
}