package com.github.lany192.arch.eventbus.util

import java.util.logging.Level

interface ILogger {
    fun log(level: Level, msg: String)
    fun log(level: Level, msg: String, th: Throwable)
}
