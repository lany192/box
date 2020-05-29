package com.github.lany192.box.config

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
     * 是否需要Toolbar
     */
    var hasToolbar = false

    /**
     * Toolbar是否需要显示返回键
     */
    var hasBackBtn = false

    /**
     * toolbar的高度
     */
    var toolbarHeight = 0

    /**
     * Toolbar颜色
     */
    @ColorRes
    var toolbarColor = 0

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
     * 内容的背景颜色
     */
    @ColorRes
    var contentColor = 0

    fun layoutId(layoutId: Int): ActivityConfig {
        this.layoutId = layoutId
        return this
    }

    fun toolBarLayoutId(toolBarLayoutId: Int): ActivityConfig {
        this.toolBarLayoutId = toolBarLayoutId
        return this
    }

    fun statusBarDarkFont(statusBarDarkFont: Boolean): ActivityConfig {
        this.statusBarDarkFont = statusBarDarkFont
        return this
    }

    fun hasToolbar(hasToolbar: Boolean): ActivityConfig {
        this.hasToolbar = hasToolbar
        return this
    }

    fun hasBackBtn(hasBackBtn: Boolean): ActivityConfig {
        this.hasBackBtn = hasBackBtn
        return this
    }

    fun toolbarHeight(toolbarHeight: Int): ActivityConfig {
        this.toolbarHeight = toolbarHeight
        return this
    }

    fun toolbarColor(@ColorRes color: Int): ActivityConfig {
        toolbarColor = color
        return this
    }

    fun keyboardEnable(keyboardEnable: Boolean): ActivityConfig {
        this.keyboardEnable = keyboardEnable
        return this
    }

    fun statusBarColor(@ColorRes color: Int): ActivityConfig {
        statusBarColor = color
        return this
    }

    fun transparentStatusBar(transparentStatusBar: Boolean): ActivityConfig {
        this.transparentStatusBar = transparentStatusBar
        return this
    }

    fun fullscreen(fullscreen: Boolean): ActivityConfig {
        this.fullscreen = fullscreen
        return this
    }

    fun title(title: CharSequence?): ActivityConfig {
        this.title = title
        return this
    }

    fun contentColor(@ColorRes color: Int): ActivityConfig {
        contentColor = color
        return this
    }
}