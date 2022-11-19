package com.github.lany192.extensions

import android.view.View

/**
 * 显示或者隐藏
 */
@JvmSynthetic
fun View.setGone(isGone: Boolean?) {
    visibility = if (isGone == true) View.GONE else View.VISIBLE
}