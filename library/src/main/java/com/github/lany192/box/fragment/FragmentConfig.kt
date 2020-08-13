package com.github.lany192.box.fragment

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes

class FragmentConfig {
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
     * 是否需要Toolbar
     */
    var isHasToolbar = false
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
     * 内容的背景颜色
     */
    @ColorRes
    var contentColor = 0
        private set

    fun layoutId(layoutId: Int): FragmentConfig {
        this.layoutId = layoutId
        return this
    }

    fun toolBarLayoutId(toolBarLayoutId: Int): FragmentConfig {
        this.toolBarLayoutId = toolBarLayoutId
        isHasToolbar = true
        return this
    }

    fun toolbarHeight(toolbarHeight: Int): FragmentConfig {
        this.toolbarHeight = toolbarHeight
        return this
    }

    fun toolbarColor(color: Int): FragmentConfig {
        this.toolbarColor = color
        return this
    }

    fun contentColor(@ColorRes color: Int): FragmentConfig {
        this.contentColor = color
        return this
    }
}