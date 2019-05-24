package com.lany.box.config;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;

import lombok.Getter;

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
     * 内容的背景颜色
     */
    @ColorRes
    private int contentColor;

    public FragmentConfig layoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public FragmentConfig toolBarLayoutId(int toolBarLayoutId) {
        this.toolBarLayoutId = toolBarLayoutId;
        this.hasToolbar = true;
        return this;
    }

    public FragmentConfig toolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
        return this;
    }

    public FragmentConfig toolbarColor(int color) {
        this.toolbarColor = color;
        return this;
    }

    public FragmentConfig contentColor(@ColorRes int color) {
        this.contentColor = color;
        return this;
    }
}
