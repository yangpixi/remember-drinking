package com.yangpixi.rememberdrinking.platform

/**
 * @author yangpixi
 * @date 2026/1/20 14:59
 * @description 安卓/ios统一接口，方便ui界面调用
 */

interface RecordSchedule {
    suspend fun doUploadRecordsWork()
}