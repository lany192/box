package com.github.lany192.arch.extension

import android.view.View

/**
 * 显示或者隐藏
 */
@JvmSynthetic
fun View.setGone(isGone: Boolean?) {
    visibility = if (isGone == true) View.GONE else View.VISIBLE
}