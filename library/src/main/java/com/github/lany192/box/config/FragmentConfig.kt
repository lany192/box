package com.github.lany192.box.config

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes

class FragmentConfig {
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
     * 是否需要Toolbar
     */
    var hasToolbar = false

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
     * 内容的背景颜色
     */
    @ColorRes
    var contentColor = 0

    fun layoutId(layoutId: Int): FragmentConfig {
        this.layoutId = layoutId
        return this
    }

    fun toolBarLayoutId(toolBarLayoutId: Int): FragmentConfig {
        this.toolBarLayoutId = toolBarLayoutId
        hasToolbar = true
        return this
    }

    fun toolbarHeight(toolbarHeight: Int): FragmentConfig {
        this.toolbarHeight = toolbarHeight
        return this
    }

    fun toolbarColor(color: Int): FragmentConfig {
        toolbarColor = color
        return this
    }

    fun contentColor(@ColorRes color: Int): FragmentConfig {
        contentColor = color
        return this
    }
}