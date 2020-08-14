package com.github.lany192.box.utils

/**
 * 防止过快双击
 */
object ClickUtil {
    @kotlin.jvm.JvmStatic
    var lastClickTime: Long = 0

    @Synchronized
    @kotlin.jvm.JvmStatic
    fun isFast(): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 500) {
            return true
        }
        lastClickTime = time
        return false
    }
}