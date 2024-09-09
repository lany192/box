package com.github.lany192.extension

import com.github.lany192.log.XLog
import com.github.lany192.utils.ContextUtils

fun Any.log(text: String) {
    if (ContextUtils.isDebug()) {
        XLog.tag(javaClass.simpleName).i(text)
    }
}
