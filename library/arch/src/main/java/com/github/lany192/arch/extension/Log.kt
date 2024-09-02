package com.github.lany192.arch.extension

import com.github.lany192.log.XLog

fun Any.log(text: String) {
    XLog.tag(javaClass.simpleName).i(text)
}
