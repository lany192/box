package com.github.lany192.box.activity

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes

class ActivityConfig {
    /**
     * 布局文件id
     */
    @LayoutRes
    var layoutId = 0

    /**
     * Toolbar布局文件id
     */
    @LayoutRes
    var toolBarLayoutId = 0

    /**
     * 状态栏的文字和图标是否改成黑色
     */
    var statusBarDarkFont = false

    /**
     * Toolbar是否需要显示返回键
     */
    var hasBackBtn = false

    /**
     * toolbar的高度
     */
    var toolbarHeight = 0

    /**
     * 是否处理软键盘
     */
    var keyboardEnable = false

    /**
     * 状态栏颜色
     */
    @ColorRes
    var statusBarColor = 0

    /**
     * 状态栏是否透明
     */
    var transparentStatusBar = false

    /**
     * 是否全屏
     */
    var fullscreen = false

    /**
     * toolbar标题
     */
    var title: CharSequence? = null

    /**
     * 是否需要Toolbar
     */
    fun hasToolbar(): Boolean {
        return toolBarLayoutId != 0
    }
}