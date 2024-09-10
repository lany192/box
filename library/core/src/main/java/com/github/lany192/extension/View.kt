package com.github.lany192.extension

import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 防止重复点击
 */
fun View.setOnClickListener(duration: Long, listener: OnClickListener) {
    this.setOnClickListener(object : OnClickListener {
        private var lastClickedTime = 0L
        override fun onClick(v: View?) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickedTime < duration) {
                return
            }
            lastClickedTime = currentTime
            listener.onClick(v)
        }
    })
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setGone(gone: Boolean) {
    visibility = if (gone) View.GONE else View.VISIBLE
}

fun View.addStatusBarPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(0, systemBars.top, 0, 0)
        insets
    }
}