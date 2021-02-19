package com.github.lany192.box.fragment

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
     * toolbar的高度
     */
    var toolbarHeight = 0

    /**
     * Toolbar颜色
     */
    @ColorRes
    var toolbarColor = 0

    /**
     * 是否toolbar覆盖方式
     */
    var isCoverStyle = false

    /**
     * 是否需要Toolbar
     */
    fun hasToolbar(): Boolean {
        return toolBarLayoutId != 0
    }
}