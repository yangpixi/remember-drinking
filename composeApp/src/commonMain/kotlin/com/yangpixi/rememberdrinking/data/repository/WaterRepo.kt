package com.yangpixi.rememberdrinking.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.yangpixi.rememberdrinking.db.Database
import com.yangpixi.rememberdrinking.domain.model.WaterRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * @author yangpixi
 * @date 2025/12/30 16:10
 * @description 单独的包装类，将数据库获取结果包装成Flow，再在viewModel里面转换成StateFlow
 */

class WaterRepo(
    private val database: Database
) {

    // 插入喝水记录
    @OptIn(ExperimentalTime::class)
    fun doDrink(intake: Long) {
        val query = database.drinkRecordQueries
        val currentTime = Clock.System.now().toEpochMilliseconds()
        query.insertRecord(intake, currentTime)
    }

    // 获取当日喝水记录
    @OptIn(ExperimentalTime::class)
    fun getDrinkListToday(): Flow<List<WaterRecord>> {
        val timeZone = TimeZone.currentSystemDefault()
        val todayTime = Clock.System.now().toLocalDateTime(timeZone).date
        val startTime = todayTime.atStartOfDayIn(timeZone).toEpochMilliseconds()
        val endTime = todayTime
            .plus(1, DateTimeUnit.DAY)
            .atStartOfDayIn(timeZone)
            .minus(1, DateTimeUnit.NANOSECOND)
            .toEpochMilliseconds()
        val query = database.drinkRecordQueries
        return query.selectAllByTimeRange(
            startTime,
            endTime,
            mapper = { id, amount_ml, record_time, is_deleted ->
                WaterRecord(id, amount_ml, record_time, is_deleted)
            })
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    @OptIn(ExperimentalTime::class)
    fun getTotalDrinkToday(): Flow<Long> {
        val query = database.drinkRecordQueries
        val timeZone = TimeZone.currentSystemDefault()
        val todayTime = Clock.System.now().toLocalDateTime(timeZone).date
        val startTime = todayTime.atStartOfDayIn(timeZone).toEpochMilliseconds()
        val endTime = todayTime
            .plus(1, DateTimeUnit.DAY)
            .atStartOfDayIn(timeZone)
            .minus(1, DateTimeUnit.NANOSECOND)
            .toEpochMilliseconds()
        return query.selectTotalDrinkToday(startTime, endTime)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.SUM ?: 0 }
    }

    // 软取消喝水记录
    fun cancelRecord(id: Long) {
        val query = database.drinkRecordQueries
        query.softDeleteById(id)
    }

    // 恢复之前取消的记录
    fun restoreRecord(id: Long) {
        val query = database.drinkRecordQueries
        query.restoreById(id)
    }

}