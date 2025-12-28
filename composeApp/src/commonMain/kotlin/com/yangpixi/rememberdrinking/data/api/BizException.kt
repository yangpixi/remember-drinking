package com.yangpixi.rememberdrinking.data.api

/**
 * @author yangpixi
 * @date 2025/12/28 12:07
 * @description 错误类
 */

class BizException(val code: Int?, message: String?) : Exception(message)