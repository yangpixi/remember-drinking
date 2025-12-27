package com.yangpixi.rememberdrinking

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform