package com.github.lany192.box.utils

/**
 * 防止过快双击
 */
object ClickUtil {
    private var lastClickTime: Long = 0

    @get:Synchronized
    val isFast: Boolean
        get() {
            val time = System.currentTimeMillis()
            if (time - lastClickTime < 500) {
                return true
            }
            lastClickTime = time
            return false
        }
}