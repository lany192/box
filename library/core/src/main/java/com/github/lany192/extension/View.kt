package com.github.lany192.extension

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 扩展方法，扩大点击区域
 * NOTE: 需要保证目标targetView有父View，否则无法扩大点击区域
 *
 * @param expandSize 扩大的大小，单位px
 */
fun View.expandTouchView(expandSize: Int = 10.dp.toInt()) {
    val parentView = (parent as? View)
    parentView?.post {
        val rect = Rect()
        getHitRect(rect) //getHitRect(rect)将视图在父容器中所占据的区域存储到rect中。
        log("rect = $rect")
        rect.left -= expandSize
        rect.top -= expandSize
        rect.right += expandSize
        rect.bottom += expandSize
        log("expandRect = $rect")
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}

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