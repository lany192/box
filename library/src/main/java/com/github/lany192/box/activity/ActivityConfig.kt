package com.github.lany192.box.activity

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes

class ActivityConfig {
    /**
     * 布局文件id
     */
    @LayoutRes
    var layoutId = 0
        private set

    /**
     * Toolbar布局文件id
     */
    @LayoutRes
    var toolBarLayoutId = 0
        private set

    /**
     * 状态栏的文字和图标是否改成黑色
     */
    var isStatusBarDarkFont = false
        private set

    /**
     * 是否需要Toolbar
     */
    var isHasToolbar = false
        private set

    /**
     * Toolbar是否需要显示返回键
     */
    var isHasBackBtn = false
        private set

    /**
     * toolbar的高度
     */
    var toolbarHeight = 0
        private set

    /**
     * Toolbar颜色
     */
    @ColorRes
    var toolbarColor = 0
        private set

    /**
     * 是否处理软键盘
     */
    var isKeyboardEnable = false
        private set

    /**
     * 状态栏颜色
     */
    @ColorRes
    var statusBarColor = 0
        private set

    /**
     * 状态栏是否透明
     */
    var isTransparentStatusBar = false
        private set

    /**
     * 是否全屏
     */
    var isFullscreen = false
        private set

    /**
     * toolbar标题
     */
    var title: CharSequence? = null
        private set

    /**
     * 内容的背景颜色
     */
    @ColorRes
    var contentColor = 0
        private set

    fun layoutId(layoutId: Int): ActivityConfig {
        this.layoutId = layoutId
        return this
    }

    fun toolBarLayoutId(toolBarLayoutId: Int): ActivityConfig {
        this.toolBarLayoutId = toolBarLayoutId
        return this
    }

    fun statusBarDarkFont(statusBarDarkFont: Boolean): ActivityConfig {
        this.isStatusBarDarkFont = statusBarDarkFont
        return this
    }

    fun hasToolbar(hasToolbar: Boolean): ActivityConfig {
        this.isHasToolbar = hasToolbar
        return this
    }

    fun hasBackBtn(hasBackBtn: Boolean): ActivityConfig {
        this.isHasBackBtn = hasBackBtn
        return this
    }

    fun toolbarHeight(toolbarHeight: Int): ActivityConfig {
        this.toolbarHeight = toolbarHeight
        return this
    }

    fun toolbarColor(@ColorRes color: Int): ActivityConfig {
        this.toolbarColor = color
        return this
    }

    fun keyboardEnable(keyboardEnable: Boolean): ActivityConfig {
        this.isKeyboardEnable = keyboardEnable
        return this
    }

    fun statusBarColor(@ColorRes color: Int): ActivityConfig {
        this.statusBarColor = color
        return this
    }

    fun transparentStatusBar(transparentStatusBar: Boolean): ActivityConfig {
        this.isTransparentStatusBar = transparentStatusBar
        return this
    }

    fun fullscreen(fullscreen: Boolean): ActivityConfig {
        this.isFullscreen = fullscreen
        return this
    }

    fun title(title: CharSequence?): ActivityConfig {
        this.title = title
        return this
    }

    fun contentColor(@ColorRes color: Int): ActivityConfig {
        this.contentColor = color
        return this
    }
}