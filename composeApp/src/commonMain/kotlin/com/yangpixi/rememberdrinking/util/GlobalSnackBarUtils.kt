package com.yangpixi.rememberdrinking.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * @author yangpixi
 * @date 2026/1/9 14:11
 * @description 提供一个全局snackbar封装
 */

class GlobalSnackBarUtils() {
    private val _uiEvent = Channel<String>(Channel.BUFFERED)

    val uiEvent = _uiEvent.receiveAsFlow()

    // 使用 trySend 更加安全，非阻塞
    fun sendEvent(msg: String) {
        _uiEvent.trySend(msg)
    }

}

