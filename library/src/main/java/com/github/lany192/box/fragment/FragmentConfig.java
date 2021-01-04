package com.github.lany192.box.fragment;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FragmentConfig {
    /**
     * 布局文件id
     */
    @LayoutRes
    private int layoutId;
    /**
     * Toolbar布局文件id
     */
    @LayoutRes
    private int toolBarLayoutId;
    /**
     * 是否需要Toolbar
     */
    private boolean hasToolbar;
    /**
     * toolbar的高度
     */
    private int toolbarHeight;
    /**
     * Toolbar颜色
     */
    @ColorRes
    private int toolbarColor;

    /**
     * 是否需要Toolbar模糊化
     */
    private boolean toolbarBlur;
    /**
     * 是否toolbar覆盖方式
     */
    private boolean coverStyle;
}
